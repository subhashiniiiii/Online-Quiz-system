
package com.incture.OnlineQuizSystem.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.incture.OnlineQuizSystem.Entity.User;
import com.incture.OnlineQuizSystem.Repository.UserRepository;
import com.incture.UserException.AuthenticationFailedException;
import com.incture.UserException.UserAlreadyExistsException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        // Initializes the mocks and prepares the test user
        MockitoAnnotations.openMocks(this);
        user = new User("testuser", "password123");
    }

    @Test
    public void testRegisterUser_Success() {
        // Given
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        // When
        User result = userService.register(user);

        // Then
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
    }
    @Test
    public void testLogin_Success() {
        // Given
        String username = "testuser";
        String password = "password123";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        userService.login(username, password);

        // Then
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testLogin_Failure_InvalidUsername() {
        // Given
        String username = "testuser";
        String password = "password123";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(AuthenticationFailedException.class, () -> userService.login(username, password));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testLogin_Failure_InvalidPassword() {
        // Given
        String username = "testuser";
        String password = "wrongpassword";
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When & Then
        assertThrows(AuthenticationFailedException.class, () -> userService.login(username, password));
        verify(userRepository, times(1)).findByUsername(username);
    }
}
