package com.incture.OnlineQuizSystem.Service;

import com.incture.OnlineQuizSystem.Entity.OnlineQuizSystem;
import com.incture.OnlineQuizSystem.Entity.QuizAttempt;
import com.incture.OnlineQuizSystem.Entity.User;
import com.incture.OnlineQuizSystem.Entity.UserAnswer;
import com.incture.OnlineQuizSystem.Repository.OnlineQuizSystemRepository;
import com.incture.OnlineQuizSystem.Repository.QuizAttemptRepository;
import com.incture.OnlineQuizSystem.Repository.UserRepository;
import com.incture.OnlineQuizSystem.Repository.UserAnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;

    @Mock
    private OnlineQuizSystemRepository quizRepository;

    @Mock
    private QuizAttemptRepository quizAttemptRepository;

    @Mock
    private UserAnswerRepository userAnswerRepository;

    @Mock
    private UserRepository userRepository;

    private OnlineQuizSystem quiz;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Setup mock data
        quiz = new OnlineQuizSystem();
        quiz.setId(1L);
        quiz.setCorrect_option("B");

        user = new User();
        user.setId(1L);
        user.setUsername("John");

        // Mock repository behavior
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    void testStartQuizAttempt() {
        // Arrange
        QuizAttempt expectedQuizAttempt = new QuizAttempt();
        expectedQuizAttempt.setUser(user);
        expectedQuizAttempt.setOnlineQuizSystem(quiz);
        expectedQuizAttempt.setStartTime(LocalDateTime.now());

        when(quizAttemptRepository.save(Mockito.any(QuizAttempt.class))).thenReturn(expectedQuizAttempt);

        // Act
        QuizAttempt quizAttempt = quizService.startQuizAttempt(1L, 1L);

        // Assert
        assertNotNull(quizAttempt);
        assertEquals(user, quizAttempt.getUser());
        assertEquals(quiz, quizAttempt.getOnlineQuizSystem());
    }

    @Test
    void testRecordAnswer() {
        // Arrange
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setId(1L);
        quizAttempt.setUser(user);
        quizAttempt.setOnlineQuizSystem(quiz);
        
        when(quizAttemptRepository.findById(1L)).thenReturn(Optional.of(quizAttempt));

        UserAnswer expectedUserAnswer = new UserAnswer(quizAttempt, 1L, "B", true);
        when(userAnswerRepository.save(Mockito.any(UserAnswer.class))).thenReturn(expectedUserAnswer);

        // Act
        UserAnswer userAnswer = quizService.recordAnswer(1L, 1L, "B");

        // Assert
        assertNotNull(userAnswer);
        assertTrue(userAnswer.isCorrect());
        assertEquals("B", userAnswer.getSelectedAnswer());
    }

    @Test
    void testCompleteQuizAttempt() {
        // Arrange
        QuizAttempt quizAttempt = new QuizAttempt();
        quizAttempt.setId(1L);
        quizAttempt.setScore(3); // Simulate 3 correct answers

        when(quizAttemptRepository.findById(1L)).thenReturn(Optional.of(quizAttempt));
        
        // Mock user answers
        when(userAnswerRepository.findByQuizAttemptId(1L)).thenReturn(List.of(
            new UserAnswer(quizAttempt, 1L, "B", true),
            new UserAnswer(quizAttempt, 2L, "A", true),
            new UserAnswer(quizAttempt, 3L, "C", true)
        ));

        // Act
        int score = quizService.completeQuizAttempt(1L);

        // Assert
        assertEquals(3, score);
        assertEquals(3, quizAttempt.getScore());
    }
}
