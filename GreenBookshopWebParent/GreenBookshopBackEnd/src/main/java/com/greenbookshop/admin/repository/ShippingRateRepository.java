package com.greenbookshop.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.greenbookshop.admin.paging.SearchRepository;
import com.greenbookshop.common.entity.ShippingRate;

public interface ShippingRateRepository extends SearchRepository<ShippingRate, Integer> {

	@Query("SELECT sr FROM ShippingRate sr WHERE sr.province.id = ?1 AND sr.district = ?2")
	public ShippingRate findByProvinceAndDistrict(Integer provinceId, String district);

	@Query("UPDATE ShippingRate sr SET sr.codSupported = ?2 WHERE sr.id = ?1")
	@Modifying
	public void updateCODSupport(Integer id, boolean enabled);

	@Query("SELECT sr FROM ShippingRate sr WHERE sr.province.name LIKE %?1% OR sr.district LIKE %?1%")
	public Page<ShippingRate> findAll(String keyword, Pageable pageable);

	public Long countById(Integer id);
}
