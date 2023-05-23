package com.greenbookshop.admin.controller;

import java.util.ArrayList;
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

import com.greenbookshop.admin.repository.DistrictRepository;
import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.District;
import com.greenbookshop.common.entity.DistrictDTO;

@RestController
public class DistrictRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DistrictRestController.class);
	
	@Autowired 
	private DistrictRepository repo;

	@GetMapping("/districts/list_by_province/{id}")
	public List<DistrictDTO> listByProvince(@PathVariable("id") Integer provinceId) {
		
		LOGGER.info("DistrictRestController | listByProvince is called");
		
		LOGGER.info("DistrictRestController | listByProvince | provinceId : " + provinceId);
		
		List<District> listDistricts = repo.findByProvinceOrderByNameAsc(new Province(provinceId));
		
		LOGGER.info("DistrictRestController | listByProvince | listDistricts.size() : " + listDistricts.size());
		
		List<DistrictDTO> result = new ArrayList<>();

		for (District district : listDistricts) {
			result.add(new DistrictDTO(district.getId(), district.getName()));
		}
		
		LOGGER.info("DistrictRestController | listByProvince | result : " + result.toString());

		return result;
	}

	@PostMapping("/districts/save")
	public String save(@RequestBody District district) {
		District savedDistrict = repo.save(district);
		return String.valueOf(savedDistrict.getId());
	}

	@DeleteMapping("/districts/delete/{id}")
	public void delete(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@PostMapping("/districts/check_unique")
	@ResponseBody
	public String checkUnique(@RequestBody Map<String,String> data) {
		
		String name = data.get("name");
		
		LOGGER.info("DistrictRestController | checkUnique is called");
		
		LOGGER.info("DistrictRestController | checkUnique | name : " + name);
		
		District provinceByName = repo.findByName(name);
//		boolean isCreatingNew = (provinceByName.getId() != null ? true : false);
		boolean isCreatingNew = (provinceByName != null ? true : false);
		
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
