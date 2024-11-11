package com.incture.OnlineQuizSystem.Controller;

import com.incture.OnlineQuizSystem.Entity.QuizAttempt;
import com.incture.OnlineQuizSystem.Entity.UserAnswer;
import com.incture.OnlineQuizSystem.Service.QuizService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
	private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    @Autowired
    private QuizService quizService;

    // Start a new quiz attempt
    @PostMapping("/startQuiz/{quizId}")
    public ResponseEntity<QuizAttempt> startQuiz(@PathVariable Long quizId,@RequestParam Long userId) {
    	try {
            logger.info("Received request to start quiz {} for user {}", quizId, userId);
        QuizAttempt quizAttempt = quizService.startQuizAttempt(quizId, userId);
        return ResponseEntity.ok(quizAttempt);
    }catch (Exception e) {
        logger.error("Error starting quiz {} for user {}: {}", quizId, userId, e.getMessage());
        return ResponseEntity.status(500).body(null);  // You can modify to handle error response properly
    }
}
    // Submit answer for a specific question in a quiz attempt
    @PostMapping("/{quizId}/attempt/{attemptId}/submitAnswer")
    public ResponseEntity<UserAnswer> submitAnswer(
            @PathVariable Long quizId, 
            @PathVariable Long attemptId,
            @RequestParam Long questionId, 
            @RequestParam String selectedAnswer) {
    	 try {
             logger.info("Received request to submit answer for quiz {} attempt {}: Answer = {}", quizId, attemptId, selectedAnswer);
        UserAnswer userAnswer = quizService.recordAnswer(attemptId, questionId, selectedAnswer);
        return ResponseEntity.ok(userAnswer);
    }catch (Exception e) {
        logger.error("Error submitting answer for quiz {} attempt {}: {}", quizId, attemptId, e.getMessage());
        return ResponseEntity.status(500).body(null);  // Handle error response properly
    }
}


    // Complete the quiz and calculate the score
    @PostMapping("/{quizId}/attempt/{attemptId}/complete")
    public ResponseEntity<Integer> completeQuiz(@PathVariable Long quizId, @PathVariable Long attemptId) {
    	try {
            logger.info("Received request to complete quiz {} attempt {}", quizId, attemptId);
        int score = quizService.completeQuizAttempt(attemptId);
        return ResponseEntity.ok(score);
    }catch (Exception e) {
        logger.error("Error completing quiz {} attempt {}: {}", quizId, attemptId, e.getMessage());
        return ResponseEntity.status(500).body(null);  // Handle error response properly
    }
}
    
    // Get result of a quiz attempt (score)
    @GetMapping("/attempt/{attemptId}/result")
    public ResponseEntity<QuizAttempt> getQuizAttemptResult(@PathVariable Long attemptId) {
        QuizAttempt result = quizService.getQuizAttemptResult(attemptId);
        return ResponseEntity.ok(result);
    }

    // Get the user's quiz history
    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<QuizAttempt>> getUserQuizHistory(@PathVariable Long userId) {
        List<QuizAttempt> history = quizService.getUserQuizHistory(userId);
        return ResponseEntity.ok(history);
    }
    @GetMapping("/user/{userId}/performanceReport")
    public ResponseEntity<Map<String, Object>> getUserPerformanceReport(@PathVariable Long userId) {
        Map<String, Object> performanceReport = quizService.getUserPerformanceReport(userId);
        return ResponseEntity.ok(performanceReport);
    }

}
