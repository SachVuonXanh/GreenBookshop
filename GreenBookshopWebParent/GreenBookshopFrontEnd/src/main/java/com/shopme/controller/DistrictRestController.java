package com.greenbookshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.District;
import com.greenbookshop.common.entity.DistrictDTO;
import com.greenbookshop.repository.DistrictRepository;

@RestController
public class DistrictRestController {

	@Autowired 
	private DistrictRepository repo;

	@GetMapping("/settings/list_districts_by_province/{id}")
	public List<DistrictDTO> listByProvince(@PathVariable("id") Integer provinceId) {
		List<District> listDistricts = repo.findByProvinceOrderByNameAsc(new Province(provinceId));
		List<DistrictDTO> result = new ArrayList<>();

		for (District district : listDistricts) {
			result.add(new DistrictDTO(district.getId(), district.getName()));
		}

		return result;
	}

}
