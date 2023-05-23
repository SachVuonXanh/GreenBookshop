package com.greenbookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.greenbookshop.common.entity.Province;

public interface ProvinceRepository extends CrudRepository<Province, Integer>{
	
	public List<Province> findAllByOrderByNameAsc();
	
	@Query("SELECT c FROM Province c WHERE c.code = ?1")
	public Province findByCode(String code);
}
