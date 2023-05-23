package com.greenbookshop.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.greenbookshop.common.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

}
