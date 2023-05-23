package com.greenbookshop.admin.service.impl;

import java.util.List;

import com.greenbookshop.admin.error.CustomerNotFoundException;
import com.greenbookshop.admin.paging.PagingAndSortingHelper;
import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.Customer;

public interface ICustomerService {

	public void listByPage(int pageNum, PagingAndSortingHelper helper);
	public void updateCustomerEnabledStatus(Integer id, boolean enabled);
	public List<Province> listAllProvinces();
	public boolean isEmailUnique(Integer id, String email);
	public void save(Customer customerInForm);
	public void delete(Integer id) throws CustomerNotFoundException;
	public Customer get(Integer id) throws CustomerNotFoundException;
	List<Customer> listAll();
}
