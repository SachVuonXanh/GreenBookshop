package com.greenbookshop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.District;

public interface DistrictRepository extends CrudRepository<District, Integer> {

	public List<District> findByProvinceOrderByNameAsc(Province province);
}
