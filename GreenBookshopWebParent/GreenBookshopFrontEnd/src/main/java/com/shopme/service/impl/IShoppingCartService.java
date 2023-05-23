package com.greenbookshop.service.impl;

import java.util.List;

import com.greenbookshop.common.entity.CartItem;
import com.greenbookshop.common.entity.Customer;
import com.greenbookshop.common.exception.ShoppingCartException;

public interface IShoppingCartService {

	public Integer addProduct(Integer productId, Integer quantity, Customer customer) 
			throws ShoppingCartException;
	
	public List<CartItem> listCartItems(Customer customer);
	
	public float updateQuantity(Integer productId, Integer quantity, Customer customer);
	
	public void removeProduct(Integer productId, Customer customer);

	public void deleteByCustomer(Customer customer);
}
