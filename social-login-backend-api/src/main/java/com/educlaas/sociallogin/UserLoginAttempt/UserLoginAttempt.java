package com.educlaas.sociallogin.UserLoginAttempt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserLoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private int attempts;

    private boolean locked;
    
    private Date lockoutExpiration;
    
    public UserLoginAttempt(Long id, String email, int attempts, boolean locked, Date lockoutExpiration) {
		super();
		this.id = id;
		this.email = email;
		this.attempts = attempts;
		this.locked = locked;
		this.lockoutExpiration = lockoutExpiration;
	}

	public UserLoginAttempt(String email, int attempts, boolean locked, Date lockoutExpiration) {
		super();
		this.email = email;
		this.attempts = attempts;
		this.locked = locked;
		this.lockoutExpiration = lockoutExpiration;
	}

	public Date getLockoutExpiration() {
		return lockoutExpiration;
	}

	public void setLockoutExpiration(Date lockoutExpiration) {
		this.lockoutExpiration = lockoutExpiration;
	}

	public void incrementAttempts() {
        this.attempts++;
    }

    public void resetAttempts() {
        this.attempts = 0;
    }

   
    public void lock() {
        this.locked = true;
    }

   
    // Constructors, getters, and setters
	public UserLoginAttempt() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

   
    
}
