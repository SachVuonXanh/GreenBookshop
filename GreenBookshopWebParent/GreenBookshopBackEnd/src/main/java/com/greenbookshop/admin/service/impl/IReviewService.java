package com.greenbookshop.admin.service.impl;

import com.greenbookshop.common.entity.Review;
import com.greenbookshop.common.exception.ReviewNotFoundException;

public interface IReviewService {

	public Review get(Integer id) throws ReviewNotFoundException;
	public void save(Review reviewInForm);
	public void delete(Integer id) throws ReviewNotFoundException;
}
