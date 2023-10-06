package com.educlaas.sociallogin;

import com.educlaas.sociallogin.UserLoginAttempt.UserLoginAttempt;
import com.educlaas.sociallogin.UserLoginAttempt.UserLoginAttemptRepository;
import com.educlaas.sociallogin.controller.LoginController;
import com.educlaas.sociallogin.exception.BadRequestException;
import com.educlaas.sociallogin.jwtsecurity.TokenProvider;
import com.educlaas.sociallogin.payload.AfterLogin;
import com.educlaas.sociallogin.payload.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private UserLoginAttemptRepository loginAttemptRepository;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoginUser_SuccessfulAuthentication() {
        // Create a sample login request
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("password");

        // Create a sample authentication result
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Create a sample user login attempt
        UserLoginAttempt userLoginAttempt = new UserLoginAttempt();
        userLoginAttempt.setEmail(login.getEmail());
        userLoginAttempt.setAttempts(3); // Simulating some previous failed attempts

        // Mock the repository's behavior
        when(loginAttemptRepository.findByEmail(login.getEmail())).thenReturn(userLoginAttempt);
        when(loginAttemptRepository.save(any(UserLoginAttempt.class))).thenReturn(userLoginAttempt);

        // Mock the token generation
        when(tokenProvider.createToken(authentication)).thenReturn("sampleToken");

        // Call the controller method
        ResponseEntity<?> response = loginController.loginUser(login);

        // Assertions
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof AfterLogin);
        AfterLogin afterLogin = (AfterLogin) response.getBody();
        assertEquals("sampleToken", afterLogin.getAccessToken());
        assertEquals(0, userLoginAttempt.getAttempts()); // Reset attempts on successful login
        assertNull(userLoginAttempt.getLockoutExpiration());
    }

    @Test
    void testLoginUser_FailedAuthentication() {
        // Create a sample login request
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("password");

        // Simulate authentication failure
        when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationException("Authentication failed") {
        });

        // Create a sample user login attempt
        UserLoginAttempt userLoginAttempt = new UserLoginAttempt();
        userLoginAttempt.setEmail(login.getEmail());
        userLoginAttempt.setAttempts(3); // Simulating some previous failed attempts

        // Mock the repository's behavior
        when(loginAttemptRepository.findByEmail(login.getEmail())).thenReturn(userLoginAttempt);
        when(loginAttemptRepository.save(any(UserLoginAttempt.class))).thenReturn(userLoginAttempt);

        // Call the controller method and expect an exception
        assertThrows(BadRequestException.class, () -> loginController.loginUser(login));

        // Verify that login attempts were incremented
        assertEquals(4, userLoginAttempt.getAttempts()); // 3 previous attempts + 1 on this failure
    }

    @Test
    void testLoginUser_AccountLocked() {
        // Create a sample login request
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("password");

        // Create a sample user login attempt with lockout
        UserLoginAttempt userLoginAttempt = new UserLoginAttempt();
        userLoginAttempt.setEmail(login.getEmail());
        userLoginAttempt.setAttempts(10); // Max attempts reached
        userLoginAttempt.lock();
        userLoginAttempt.setLockoutExpiration(new Date(System.currentTimeMillis() + 10000L)); // Locked for 10 seconds

        // Mock the repository's behavior
        when(loginAttemptRepository.findByEmail(login.getEmail())).thenReturn(userLoginAttempt);

        // Call the controller method and expect an exception
        assertThrows(BadRequestException.class, () -> loginController.loginUser(login));

        // Verify that account is locked
        assertTrue(loginController.isAccountLocked(userLoginAttempt));
    }
}
