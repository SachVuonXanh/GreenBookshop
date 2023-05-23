package com.greenbookshop.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenbookshop.admin.service.CategoryService;

@RestController
public class CategoryRestController {

	@Autowired
	private CategoryService service;

	@PostMapping("/categories/check_unique")
	public String checkUnique(Integer id, String name, String alias){
		return service.checkUnique(id, name, alias);
	}
}
