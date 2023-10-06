package com.educlaas.sociallogin.UserLoginAttempt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserLoginAttemptRepository loginAttemptRepository;

    @Autowired
    public LoginService(UserLoginAttemptRepository loginAttemptRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
    }

    public UserLoginAttempt getLoginAttemptByEmail(String email) {
        return loginAttemptRepository.findByEmail(email);
    }

    public UserLoginAttempt incrementLoginAttempts(UserLoginAttempt loginAttempt, int maxLoginAttempts, long lockoutDurationMillis) {
        loginAttempt.incrementAttempts();
        if (loginAttempt.getAttempts() >= maxLoginAttempts) {
            loginAttempt.lock();
            // Set the lockout expiration time
            loginAttempt.setLockoutExpiration(new Date(System.currentTimeMillis() + lockoutDurationMillis));
        }
        return loginAttemptRepository.save(loginAttempt);
    }

    public UserLoginAttempt resetLoginAttempts(UserLoginAttempt loginAttempt) {
        loginAttempt.resetAttempts();
        loginAttempt.setLockoutExpiration(null); // Reset the lockout expiration time
        return loginAttemptRepository.save(loginAttempt);
    }
}

