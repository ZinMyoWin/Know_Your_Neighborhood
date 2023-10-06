package com.educlaas.sociallogin.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educlaas.sociallogin.UserLoginAttempt.UserLoginAttempt;
import com.educlaas.sociallogin.UserLoginAttempt.UserLoginAttemptRepository;
import com.educlaas.sociallogin.exception.BadRequestException;
import com.educlaas.sociallogin.jwtsecurity.TokenProvider;
import com.educlaas.sociallogin.payload.AfterLogin;
import com.educlaas.sociallogin.payload.Login;

@RestController
@RequestMapping(value = "/kyn")
public class LoginController {

    private static final int MAX_LOGIN_ATTEMPTS = 4; // Define the maximum login attempts here
    private static final long LOCKOUT_DURATION = 5 * 60 * 1000;

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserLoginAttemptRepository loginAttemptRepository;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserLoginAttemptRepository loginAttemptRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.loginAttemptRepository = loginAttemptRepository;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody Login login) {

        // Check if the user is currently locked out
        UserLoginAttempt loginAttempt = loginAttemptRepository.findByEmail(login.getEmail());
        if (loginAttempt != null && isAccountLocked(loginAttempt)) {
            throw new BadRequestException("Account is temporarily locked. Please try again later.");
        }
        if (loginAttempt ==null) {
        	throw new BadRequestException("No user with "+ login.getEmail()+" found.");
        }

        // Attempt authentication
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        } catch (AuthenticationException e) {
            // Authentication failed; increment login attempts and check for lockout
            loginAttempt = incrementLoginAttempts(loginAttempt);
            throw new BadRequestException("Incorrect Password");
        }

        // Authentication succeeded; reset login attempts
        	loginAttempt = resetLoginAttempts(loginAttempt);
			
		

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // If authentication is correct, provide the token
        String token = tokenProvider.createToken(authentication);

        return ResponseEntity.ok(new AfterLogin(token));
    }

    public boolean isAccountLocked(UserLoginAttempt loginAttempt) {
        // Check if the account is locked based on the lockout expiration time
        return loginAttempt.isLocked() && loginAttempt.getLockoutExpiration() != null &&
               loginAttempt.getLockoutExpiration().after(new Date());
    }

    private UserLoginAttempt incrementLoginAttempts(UserLoginAttempt loginAttempt) {
        loginAttempt.incrementAttempts();
        if (loginAttempt.getAttempts() >= MAX_LOGIN_ATTEMPTS) {
            loginAttempt.lock();
            // Set the lockout expiration time
            loginAttempt.setLockoutExpiration(new Date(System.currentTimeMillis() + LOCKOUT_DURATION));
        }
        return loginAttemptRepository.save(loginAttempt);
    }

    private UserLoginAttempt resetLoginAttempts(UserLoginAttempt loginAttempt) {
        loginAttempt.resetAttempts();
        loginAttempt.setLockoutExpiration(null); // Reset the lockout expiration time
        return loginAttemptRepository.save(loginAttempt);
    }
}
