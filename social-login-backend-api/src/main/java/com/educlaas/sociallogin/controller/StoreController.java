package com.educlaas.sociallogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educlaas.sociallogin.dao.Store;

import com.educlaas.sociallogin.service.StoreServiceImpl;

@RestController
@RequestMapping(value = "/kyn")
public class StoreController {

	@Autowired
	private StoreServiceImpl storeServiceImpl;

	//view store
	@GetMapping(value = "/viewStores")
	public List<Store> viewStores(){
		return storeServiceImpl.viewStores();
	}

	//search with keyword
	@GetMapping(value = "/search/{keywords}")
	public List<Store> searchByKeyword(@PathVariable String keywords){
		return storeServiceImpl.searchByKeyword(keywords);
	}
}