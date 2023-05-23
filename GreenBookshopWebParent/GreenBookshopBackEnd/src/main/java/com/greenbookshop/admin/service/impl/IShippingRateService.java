package com.greenbookshop.admin.service.impl;

import java.util.List;

import com.greenbookshop.admin.paging.PagingAndSortingHelper;
import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.ShippingRate;
import com.greenbookshop.common.exception.ShippingRateAlreadyExistsException;
import com.greenbookshop.common.exception.ShippingRateNotFoundException;

public interface IShippingRateService {

	public void listByPage(int pageNum, PagingAndSortingHelper helper);
	public List<Province> listAllProvinces();
	public void save(ShippingRate rateInForm) throws ShippingRateAlreadyExistsException;
	public ShippingRate get(Integer id) throws ShippingRateNotFoundException;
	public void updateCODSupport(Integer id, boolean codSupported) throws ShippingRateNotFoundException;
	public void delete(Integer id) throws ShippingRateNotFoundException;
	
}
