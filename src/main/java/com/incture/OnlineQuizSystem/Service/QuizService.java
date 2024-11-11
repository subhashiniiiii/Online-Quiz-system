package com.incture.OnlineQuizSystem.Service;

import com.incture.OnlineQuizSystem.Entity.OnlineQuizSystem;
import com.incture.OnlineQuizSystem.Entity.QuizAttempt;
import com.incture.OnlineQuizSystem.Entity.User;
import com.incture.OnlineQuizSystem.Entity.UserAnswer;
import com.incture.OnlineQuizSystem.Repository.OnlineQuizSystemRepository;
import com.incture.OnlineQuizSystem.Repository.QuizAttemptRepository;
import com.incture.OnlineQuizSystem.Repository.UserAnswerRepository;
import com.incture.OnlineQuizSystem.Repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {
	private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    @Autowired
    private OnlineQuizSystemRepository quizRepository;

    @Autowired
    private QuizAttemptRepository quizAttemptRepository;

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to start a new quiz attempt
    public QuizAttempt startQuizAttempt(Long quizId, Long userId) {
    	 try {
             logger.info("Starting quiz attempt for user {} on quiz {}", userId, quizId);
        // Fetch the quiz and user from the database
        OnlineQuizSystem quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create a new QuizAttempt with the quiz and user set
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setUser(user); // Use the fetched user
        quizAttempt.setTotalQuestions(1); // Set the total number of questions in the quiz
        quizAttempt.setOnlineQuizSystem(quiz); // Set quiz to avoid null quiz_id error
        quizAttempt.setStartTime(LocalDateTime.now());
        quizAttempt.setScore(0); // Initialize score to 0

        // Save and return the quiz attempt
        logger.info("Quiz attempt started for user {} with quiz ID {}", userId, quizId);
        return quizAttemptRepository.save(quizAttempt);
    }catch (Exception e) {
        logger.error("Error starting quiz attempt for user {}: {}", userId, e.getMessage());
        throw e;
    }
    }

    // Method to record a user's answer for a specific question in a quiz attempt
    public UserAnswer recordAnswer(Long attemptId, Long questionId, String selectedAnswer) {
    	try {
            logger.info("Recording answer for attempt {}: Question {} with answer {}", attemptId, questionId, selectedAnswer);
        // Fetch the QuizAttempt by ID
        QuizAttempt quizAttempt = quizAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Quiz attempt not found"));

        // Fetch the question by ID
        OnlineQuizSystem question = quizRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Check if the selected answer is correct
        boolean isCorrect = question.getCorrect_option().trim().equalsIgnoreCase(selectedAnswer.trim());
        if (isCorrect) {
            quizAttempt.incrementCorrectAnswers();
        } else {
            quizAttempt.incrementIncorrectAnswers();
        }
        
        // Create and save the user's answer
        UserAnswer userAnswer = new UserAnswer(quizAttempt, questionId, selectedAnswer, isCorrect);
        userAnswerRepository.save(userAnswer);
        logger.info("Answer recorded for attempt {}: {}", attemptId, isCorrect ? "Correct" : "Incorrect");
        return userAnswer;
    }catch (Exception e) {
        logger.error("Error recording answer for attempt {}: {}", attemptId, e.getMessage());
        throw e;
    }
}

    // Method to complete the quiz attempt and calculate the score
    public int completeQuizAttempt(Long attemptId) {
    	 try {
             logger.info("Completing quiz attempt {}", attemptId);
        // Fetch only the answers associated with this quiz attempt
        List<UserAnswer> userAnswers = userAnswerRepository.findByQuizAttemptId(attemptId);

        // Calculate score by counting correct answers
        int score = (int) userAnswers.stream().filter(UserAnswer::isCorrect).count();

        // Update the quiz attempt with the final score
        QuizAttempt quizAttempt = quizAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Quiz attempt not found"));
        quizAttempt.setScore(score);
        quizAttempt.setEndTime(LocalDateTime.now());
        quizAttemptRepository.save(quizAttempt);
        logger.info("Quiz attempt {} completed with score {}", attemptId, score);
        return score;
    	 }
        catch (Exception e) {
            logger.error("Error completing quiz attempt {}: {}", attemptId, e.getMessage());
            throw e;
        }
    }

    // Method to get the result of a quiz attempt (i.e., the score)
    public QuizAttempt getQuizAttemptResult(Long attemptId) {
        return quizAttemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Quiz attempt not found"));
    }

    // Method to get the user's quiz history
    public List<QuizAttempt> getUserQuizHistory(Long userId) {
        return quizAttemptRepository.findByUserId(userId);
    }
    // Method to generate user performance report
    public Map<String, Object> getUserPerformanceReport(Long userId) {
        // Get all quiz attempts by the user
        List<QuizAttempt> attempts = quizAttemptRepository.findByUserId(userId);

        // Prepare a map to hold the detailed report
        Map<String, Object> performanceReport = new HashMap<>();
        
        int totalScore = 0;
        int totalAttempts = attempts.size();
        int totalCorrectAnswers = 0;
        int totalIncorrectAnswers = 0;
        
        for (QuizAttempt attempt : attempts) {
            // Update the total score
            totalScore += attempt.getScore();
            
            // Track correct and incorrect answers
            totalCorrectAnswers += attempt.getCorrectAnswers();
            totalIncorrectAnswers += attempt.getIncorrectAnswers();
        }
        
        // Add the data to the report map
        performanceReport.put("totalAttempts", totalAttempts);
        performanceReport.put("totalScore", totalScore);
        performanceReport.put("totalCorrectAnswers", totalCorrectAnswers);
        performanceReport.put("totalIncorrectAnswers", totalIncorrectAnswers);
        
        // You could also add the average score per quiz attempt, etc.
        if (totalAttempts > 0) {
            double avgScore = (double) totalScore / totalAttempts;
            performanceReport.put("averageScore", avgScore);
        }

        return performanceReport;
    }

}
