package com.educlaas.sociallogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.educlaas.sociallogin.dao.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>{
	
	
	 //Check or Find existing mail - return true or false
    //For Register (duplicate mail)
    Boolean existsByEmail(String email);
    
    //Check for the Login Email exist in the database
    Optional<Users> findByEmail(String email);
}
