package com.greenbookshop.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.greenbookshop.common.entity.Province;

public interface ProvinceRepository extends CrudRepository<Province, Integer> {
	
	public List<Province> findAllByOrderByNameAsc();
	
	@Query("SELECT c FROM Province c WHERE c.name = :name")
	public Province findByName(String name);
}
