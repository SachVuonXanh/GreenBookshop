package com.greenbookshop.repository;

import org.springframework.data.repository.CrudRepository;

import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {

	public ShippingRate findByProvinceAndDistrict(Province province, String district);
	
}