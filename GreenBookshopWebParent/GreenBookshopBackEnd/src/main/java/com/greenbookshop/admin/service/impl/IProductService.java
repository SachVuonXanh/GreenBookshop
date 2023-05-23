package com.greenbookshop.admin.service.impl;

import java.util.List;

import com.greenbookshop.admin.paging.PagingAndSortingHelper;
import com.greenbookshop.common.entity.product.Product;
import com.greenbookshop.common.exception.ProductNotFoundException;

public interface IProductService {

	public List<Product> listAll();
	
	public Product save(Product product);
	
	public String checkUnique(Integer id, String name);
	
	public void updateProductEnabledStatus(Integer id, boolean enabled);
	
	public void delete(Integer id) throws ProductNotFoundException;
	
	public Product get(Integer id) throws ProductNotFoundException;
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId);
	
	public void saveProductPrice(Product productInForm);
	
}
