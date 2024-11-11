package com.incture.OnlineQuizSystem.Controller;

import com.incture.OnlineQuizSystem.Entity.QuizAttempt;
import com.incture.OnlineQuizSystem.Entity.UserAnswer;
import com.incture.OnlineQuizSystem.Service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class QuizControllerTest {

    @Mock
    private QuizService quizService;

    @InjectMocks
    private QuizController quizController;

    private Long quizId = 1L;
    private Long userId = 1L;
    private QuizAttempt quizAttempt;
    private UserAnswer userAnswer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        quizAttempt = new QuizAttempt();
        quizAttempt.setId(1L);
        quizAttempt.setScore(0);
        
        userAnswer = new UserAnswer();
        userAnswer.setQuestionId(1L);
        userAnswer.setSelectedAnswer("A");
        userAnswer.setCorrect(true);
    }

    @Test
    void testStartQuiz() {
        // Arrange
        when(quizService.startQuizAttempt(quizId, userId)).thenReturn(quizAttempt);

        // Act
        ResponseEntity<QuizAttempt> response = quizController.startQuiz(quizId, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(quizAttempt, response.getBody());
    }

    @Test
    void testStartQuiz_Error() {
        // Arrange
        when(quizService.startQuizAttempt(quizId, userId)).thenThrow(new RuntimeException("Quiz not found"));

        // Act
        ResponseEntity<QuizAttempt> response = quizController.startQuiz(quizId, userId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testSubmitAnswer() {
        // Arrange
        Long attemptId = 1L;
        Long questionId = 1L;
        String selectedAnswer = "A";
        when(quizService.recordAnswer(attemptId, questionId, selectedAnswer)).thenReturn(userAnswer);

        // Act
        ResponseEntity<UserAnswer> response = quizController.submitAnswer(quizId, attemptId, questionId, selectedAnswer);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userAnswer, response.getBody());
    }

    @Test
    void testSubmitAnswer_Error() {
        // Arrange
        Long attemptId = 1L;
        Long questionId = 1L;
        String selectedAnswer = "A";
        when(quizService.recordAnswer(attemptId, questionId, selectedAnswer)).thenThrow(new RuntimeException("Error"));

        // Act
        ResponseEntity<UserAnswer> response = quizController.submitAnswer(quizId, attemptId, questionId, selectedAnswer);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
