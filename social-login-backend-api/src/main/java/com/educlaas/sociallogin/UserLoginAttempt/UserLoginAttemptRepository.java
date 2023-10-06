package com.educlaas.sociallogin.UserLoginAttempt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginAttemptRepository extends JpaRepository<UserLoginAttempt, Long> {
    UserLoginAttempt findByEmail(String email);
}
