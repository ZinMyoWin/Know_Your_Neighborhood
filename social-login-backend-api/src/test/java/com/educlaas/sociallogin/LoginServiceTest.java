package com.educlaas.sociallogin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.educlaas.sociallogin.UserLoginAttempt.LoginService;
import com.educlaas.sociallogin.UserLoginAttempt.UserLoginAttempt;
import com.educlaas.sociallogin.UserLoginAttempt.UserLoginAttemptRepository;

import java.util.Date;

class LoginServiceTest {

    @Mock
    private UserLoginAttemptRepository loginAttemptRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetLoginAttemptByEmail() {
        // Create a sample UserLoginAttempt
        UserLoginAttempt sampleAttempt = new UserLoginAttempt();
        sampleAttempt.setEmail("test@example.com");

        // Mock the repository's behavior
        when(loginAttemptRepository.findByEmail("test@example.com")).thenReturn(sampleAttempt);

        // Test the method
        UserLoginAttempt result = loginService.getLoginAttemptByEmail("test@example.com");

        // Assertions
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testIncrementLoginAttempts() {
        // Create a sample UserLoginAttempt
        UserLoginAttempt sampleAttempt = new UserLoginAttempt();
        sampleAttempt.setEmail("test@example.com");

        // Mock the repository's behavior
        when(loginAttemptRepository.save(any(UserLoginAttempt.class))).thenReturn(sampleAttempt);

        // Test the method
        UserLoginAttempt result = loginService.incrementLoginAttempts(sampleAttempt, 3, 60000L);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.getAttempts()); // Assuming it starts at 0
       
    }

    @Test
    void testResetLoginAttempts() {
        // Create a sample UserLoginAttempt with previous attempts and lockout expiration
        UserLoginAttempt sampleAttempt = new UserLoginAttempt();
        sampleAttempt.setEmail("test@example.com");
        sampleAttempt.setAttempts(3);
        sampleAttempt.setLockoutExpiration(new Date(System.currentTimeMillis() + 60000L));

        // Mock the repository's behavior
        when(loginAttemptRepository.save(any(UserLoginAttempt.class))).thenReturn(sampleAttempt);

        // Test the method
        UserLoginAttempt result = loginService.resetLoginAttempts(sampleAttempt);

        // Assertions
        assertNotNull(result);
        assertEquals(0, result.getAttempts());
        assertNull(result.getLockoutExpiration());
    }
}
