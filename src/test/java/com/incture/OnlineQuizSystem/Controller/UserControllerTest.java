package com.incture.OnlineQuizSystem.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.incture.OnlineQuizSystem.Entity.User;
import com.incture.OnlineQuizSystem.Service.UserService;
import com.incture.UserException.AuthenticationFailedException;
import com.incture.UserException.UserAlreadyExistsException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setUp() {
        // Initializes the mock objects and prepares a test user
        MockitoAnnotations.openMocks(this);
        user = new User("testuser", "password123");
    }
    @Test
    public void testLoginUser_Success() throws Exception {
        // Given
        String loginRequestJson = "{\"username\": \"testuser\", \"password\": \"password123\"}";
        doNothing().when(userService).login("testuser", "password123");

        // When & Then
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Login successful"));

        verify(userService, times(1)).login("testuser", "password123");
    }

    @Test
    public void testLoginUser_Failure_InvalidUsername() throws Exception {
        // Given
        String loginRequestJson = "{\"username\": \"wronguser\", \"password\": \"password123\"}";
        doThrow(new AuthenticationFailedException("Invalid username or password")).when(userService).login("wronguser", "password123");

        // When & Then
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));

        verify(userService, times(1)).login("wronguser", "password123");
    }

    @Test
    public void testLoginUser_Failure_InvalidPassword() throws Exception {
        // Given
        String loginRequestJson = "{\"username\": \"testuser\", \"password\": \"wrongpassword\"}";
        doThrow(new AuthenticationFailedException("Invalid username or password")).when(userService).login("testuser", "wrongpassword");

        // When & Then
        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));

        verify(userService, times(1)).login("testuser", "wrongpassword");
    }
}
