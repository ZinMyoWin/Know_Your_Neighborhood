package com.educlaas.sociallogin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.educlaas.sociallogin.dao.Store;

@Service
public interface StoreService {

	public List<Store> viewStores();
	public List<Store> searchByKeyword(String keywords);

 

}
