package com.greenbookshop.admin.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.greenbookshop.admin.repository.ProvinceRepository;
import com.greenbookshop.common.entity.Province;

@RestController
public class ProvinceRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProvinceRestController.class);

	@Autowired 
	private ProvinceRepository repo;

	@GetMapping("/provinces/list")
	public List<Province> listAll() {
		return repo.findAllByOrderByNameAsc();
	}

	@PostMapping("/provinces/save")
	public String save(@RequestBody Province province) {
		LOGGER.info("ProvinceRestController | saveProvince is called");

		Province savedProvince = repo.save(province);

		LOGGER.info("ProvinceRestController | after save Province");
		return String.valueOf(savedProvince.getId());
	}

	@DeleteMapping("/provinces/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@PostMapping("/provinces/check_unique")
	@ResponseBody
	public String checkUnique(@RequestBody Map<String,String> data) {
		
		String name = data.get("name");
		
		LOGGER.info("ProvinceRestController | checkUnique is called");
		
		LOGGER.info("ProvinceRestController | checkUnique | name : " + name);
		
		Province provinceByName = repo.findByName(name);
		boolean isCreatingNew = (provinceByName.getId() != null ? true : false);
		
		if (isCreatingNew) {
			if (provinceByName != null) return "Duplicate";
		} else {
			if (provinceByName != null && provinceByName.getId() != null) {
				return "Duplicate";
			}
		}
		
		return "OK";
	}
	
}
