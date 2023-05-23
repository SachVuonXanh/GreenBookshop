package com.greenbookshop.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenbookshop.admin.dto.CategoryDTO;
import com.greenbookshop.admin.error.BrandNotFoundException;
import com.greenbookshop.admin.error.BrandNotFoundRestException;
import com.greenbookshop.admin.service.BrandService;
import com.greenbookshop.common.entity.Brand;
import com.greenbookshop.common.entity.Category;

@RestController
public class BrandRestController {
	
	@Autowired
	private BrandService service;

	@PostMapping("/brands/check_unique")
	public String checkUnique(Integer id,String name) {
		return service.checkUnique(id, name);
	}
	
	@GetMapping("/brands/{id}/categories")
	public List<CategoryDTO> listCategoriesByBrand(@PathVariable(name = "id") Integer brandId) throws BrandNotFoundRestException {
		List<CategoryDTO> listCategories = new ArrayList<>(); 

		try {
			Brand brand = service.get(brandId);
			Set<Category> categories = brand.getCategories();

			for (Category category : categories) {
				CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
				listCategories.add(dto);
			}

			return listCategories;
		} catch (BrandNotFoundException e) {
			throw new BrandNotFoundRestException();
		}
	}
}