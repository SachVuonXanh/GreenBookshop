package com.greenbookshop.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenbookshop.admin.service.ShippingRateService;
import com.greenbookshop.common.exception.ShippingRateNotFoundException;

@RestController
public class ShippingRateRestController {

	@Autowired 
	private ShippingRateService service;
	

	@PostMapping("/get_shipping_cost")
	public String getShippingCost(Integer productId, Integer provinceId, String district) 
			throws ShippingRateNotFoundException {
		float shippingCost = service.calculateShippingCost(productId, provinceId, district);
		return String.valueOf(shippingCost);
	}
}
