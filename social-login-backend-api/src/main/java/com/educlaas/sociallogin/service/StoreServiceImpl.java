package com.educlaas.sociallogin.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educlaas.sociallogin.dao.Store;
import com.educlaas.sociallogin.repository.StoreRepository;


@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreRepository storeRepo;

	@Override
	public List<Store> viewStores() {
		// TODO Auto-generated method stub
		return storeRepo.findAll();
	}

	@Override
	public List<Store> searchByKeyword(String keywords) {
		// TODO Auto-generated method stub
		return storeRepo.searchStoreByKeyword(keywords);
	}

 

}
