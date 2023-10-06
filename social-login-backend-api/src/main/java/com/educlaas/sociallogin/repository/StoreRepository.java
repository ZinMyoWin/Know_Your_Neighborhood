package com.educlaas.sociallogin.repository;


import java.util.List;

 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.educlaas.sociallogin.dao.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

 

	@Query(nativeQuery = true, value = "Select * from store " 
					+ "where store_name like %:keywords% or store_type like %:keywords% or location like %:keywords% ;")
	public List<Store> searchStoreByKeyword(@Param ("keywords") String keywords);
	
}
