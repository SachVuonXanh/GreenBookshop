package com.greenbookshop.repository;

import org.springframework.data.repository.CrudRepository;

import com.greenbookshop.common.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

}
