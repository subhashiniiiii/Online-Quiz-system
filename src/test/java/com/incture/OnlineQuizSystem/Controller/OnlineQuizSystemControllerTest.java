package com.incture.OnlineQuizSystem.Controller;

import com.incture.OnlineQuizSystem.Entity.OnlineQuizSystem;
import com.incture.OnlineQuizSystem.Service.OnlineQuizSystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;

class OnlineQuizSystemControllerTest {

    @Mock
    private OnlineQuizSystemService onlinequizsystemService;

    @InjectMocks
    private OnlineQuizSystemController onlinequizsystemController;

    private MockMvc mockMvc;

    private OnlineQuizSystem question1;
    private OnlineQuizSystem question2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(onlinequizsystemController).build();

        question1 = new OnlineQuizSystem();
        question1.setQuestion_test("What is the capital of France?");
        question1.setOption_a("Berlin");
        question1.setOption_b("Paris");
        question1.setOption_c("Madrid");
        question1.setOption_d("Lisbon");
        question1.setCorrect_option("Paris");
        question1.setCategory("Geography");
        question1.setDifficulty("Easy");

        question2 = new OnlineQuizSystem();
        question2.setQuestion_test("What is 2+3");
        question2.setOption_a("10");
        question2.setOption_b("9");
        question2.setOption_c("7");
        question2.setOption_d("5");
        question2.setCorrect_option("5");
        question2.setCategory("Math");
        question2.setDifficulty("Easy");
    }

    @Test
    void testGetAllQuestions() throws Exception {
        when(onlinequizsystemService.getAllQuestions()).thenReturn(Arrays.asList(question1, question2));
        
        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].question_test").value("What is the capital of France?"))
                .andExpect(jsonPath("$[1].question_test").value("What is 2+3"));
        
        verify(onlinequizsystemService, times(1)).getAllQuestions();
    }
}
