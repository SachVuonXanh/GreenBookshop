package com.greenbookshop.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenbookshop.common.entity.Address;
import com.greenbookshop.common.entity.Customer;
import com.greenbookshop.common.entity.ShippingRate;
import com.greenbookshop.repository.ShippingRateRepository;

@Service
@Transactional
public class ShippingRateService {

	@Autowired 
	private ShippingRateRepository repo;

	public ShippingRate getShippingRateForCustomer(Customer customer) {
		String district = customer.getDistrict();
		if (district == null || district.isEmpty()) {
			district = customer.getCommune();
		}

		return repo.findByProvinceAndDistrict(customer.getProvince(), district);
	}

	public ShippingRate getShippingRateForAddress(Address address) {
		String district = address.getDistrict();
		if (district == null || district.isEmpty()) {
			district = address.getCommune();
		}

		return repo.findByProvinceAndDistrict(address.getProvince(), district);
	}	
}