package com.greenbookshop.admin.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greenbookshop.admin.error.OrderNotFoundException;
import com.greenbookshop.admin.paging.PagingAndSortingHelper;
import com.greenbookshop.admin.repository.ProvinceRepository;
import com.greenbookshop.admin.repository.OrderRepository;
import com.greenbookshop.admin.service.impl.IOrderService;
import com.greenbookshop.common.entity.Province;
import com.greenbookshop.common.entity.order.Order;
import com.greenbookshop.common.entity.order.OrderStatus;
import com.greenbookshop.common.entity.order.OrderTrack;

@Service
public class OrderService implements IOrderService{

	private static final int ORDERS_PER_PAGE = 10;

	private OrderRepository orderRepo;
	
	private ProvinceRepository provinceRepo;
	
	
	public OrderService(OrderRepository orderRepo, ProvinceRepository provinceRepo) {
		super();
		this.orderRepo = orderRepo;
		this.provinceRepo = provinceRepo;
	}

	@Override
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		
		String sortField = helper.getSortField();
		String sortDir = helper.getSortDir();
		String keyword = helper.getKeyword();

		Sort sort = null;

		if ("destination".equals(sortField)) {
			sort = Sort.by("province").and(Sort.by("district")).and(Sort.by("commune"));
		} else {
			sort = Sort.by(sortField);
		}

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE, sort);

		Page<Order> page = null;

		if (keyword != null) {
			page = orderRepo.findAll(keyword, pageable);
		} else {
			page = orderRepo.findAll(pageable);
		}

		helper.updateModelAttributes(pageNum, page);		
	}
	
	@Override
	public Order get(Integer id) throws OrderNotFoundException {
		try {
			return orderRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new OrderNotFoundException("Could not find any orders with ID " + id);
		}
	}
	
	@Override
	public void delete(Integer id) throws OrderNotFoundException {
		Long count = orderRepo.countById(id);
		if (count == null || count == 0) {
			throw new OrderNotFoundException("Could not find any orders with ID " + id); 
		}

		orderRepo.deleteById(id);
	}
	
	public List<Province> listAllProvinces() {
		return provinceRepo.findAllByOrderByNameAsc();
	}
	
	public void save(Order orderInForm) {
		Order orderInDB = orderRepo.findById(orderInForm.getId()).get();
		orderInForm.setOrderTime(orderInDB.getOrderTime());
		orderInForm.setCustomer(orderInDB.getCustomer());

		orderRepo.save(orderInForm);
	}
	
	public void updateStatus(Integer orderId, String status) {
		Order orderInDB = orderRepo.findById(orderId).get();
		OrderStatus statusToUpdate = OrderStatus.valueOf(status);

		if (!orderInDB.hasStatus(statusToUpdate)) {
			List<OrderTrack> orderTracks = orderInDB.getOrderTracks();

			OrderTrack track = new OrderTrack();
			track.setOrder(orderInDB);
			track.setStatus(statusToUpdate);
			track.setUpdatedTime(new Date());
			track.setNotes(statusToUpdate.defaultDescription());

			orderTracks.add(track);

			orderInDB.setStatus(statusToUpdate);

			orderRepo.save(orderInDB);
		}

	}
}
