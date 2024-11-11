package com.incture.OnlineQuizSystem.Service;

import com.incture.OnlineQuizSystem.Entity.OnlineQuizSystem;
import com.incture.OnlineQuizSystem.Repository.OnlineQuizSystemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class OnlineQuizSystemServiceTest {

    @Mock
    private OnlineQuizSystemRepository onlinequizsystemRepository;

    @InjectMocks
    private OnlineQuizSystemService onlinequizsystemService;

    private OnlineQuizSystem onlineQuizSystem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        onlineQuizSystem = new OnlineQuizSystem();
        onlineQuizSystem.setQuestion_test("What is the capital of France?");
        onlineQuizSystem.setOption_a("Berlin");
        onlineQuizSystem.setOption_b("Paris");
        onlineQuizSystem.setOption_c("Madrid");
        onlineQuizSystem.setOption_d("Lisbon");
        onlineQuizSystem.setCorrect_option("Paris");
        onlineQuizSystem.setCategory("Geography");
        onlineQuizSystem.setDifficulty("Easy");
    }

    @Test
    void testAddQuestion() {
        when(onlinequizsystemRepository.save(any(OnlineQuizSystem.class))).thenReturn(onlineQuizSystem);
        
        OnlineQuizSystem savedQuestion = onlinequizsystemService.addQuestion(onlineQuizSystem);
        
        assertNotNull(savedQuestion);
        assertEquals("What is the capital of France?", savedQuestion.getQuestion_test());
        verify(onlinequizsystemRepository, times(1)).save(any(OnlineQuizSystem.class));
    }
    
    void testGetQuestionsByCategory() {
        List<OnlineQuizSystem> expectedQuestions = Arrays.asList(onlineQuizSystem);
        when(onlinequizsystemRepository.findByCategory("Geography")).thenReturn(expectedQuestions);
        
        List<OnlineQuizSystem> actualQuestions = onlinequizsystemService.getQuestionsByCategory("Geography");
        
        assertNotNull(actualQuestions);
        assertEquals(1, actualQuestions.size());
        assertEquals("What is the capital of France?", actualQuestions.get(0).getQuestion_test());
        verify(onlinequizsystemRepository, times(1)).findByCategory("Geography");
    }
}
