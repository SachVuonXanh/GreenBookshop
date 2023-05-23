package com.greenbookshop.service.impl;

import org.springframework.data.domain.Page;

import com.greenbookshop.common.entity.Customer;
import com.greenbookshop.common.entity.Review;
import com.greenbookshop.common.exception.ReviewNotFoundException;

public interface IReviewService {

	public Page<Review> listByCustomerByPage(Customer customer, String keyword, int pageNum, 
			String sortField, String sortDir);
	
	public Review getByCustomerAndId(Customer customer, Integer reviewId) throws ReviewNotFoundException;
}
