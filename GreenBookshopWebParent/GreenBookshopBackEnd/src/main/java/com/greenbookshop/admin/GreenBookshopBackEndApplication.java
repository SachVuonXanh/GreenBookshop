package com.greenbookshop.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.greenbookshop.common.entity"})
public class GreenBookshopBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenBookshopBackEndApplication.class, args);
	}

}
