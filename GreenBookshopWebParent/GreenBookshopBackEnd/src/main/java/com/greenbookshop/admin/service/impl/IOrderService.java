package com.greenbookshop.admin.service.impl;

import com.greenbookshop.admin.error.OrderNotFoundException;
import com.greenbookshop.admin.paging.PagingAndSortingHelper;
import com.greenbookshop.common.entity.order.Order;

public interface IOrderService {

	public void listByPage(int pageNum, PagingAndSortingHelper helper);

	public Order get(Integer id) throws OrderNotFoundException;

	public void delete(Integer id) throws OrderNotFoundException;
}
