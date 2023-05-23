package com.greenbookshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenbookshop.common.entity.Customer;
import com.greenbookshop.common.exception.ShoppingCartException;
import com.greenbookshop.service.CustomerService;
import com.greenbookshop.service.ShoppingCartService;
import com.greenbookshop.util.AuthenticationControllerHelperUtil;

@RestController
public class ShoppingCartRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartRestController.class);
	
	private ShoppingCartService cartService;
	
	private AuthenticationControllerHelperUtil authenticationControllerHelperUtil;
	
	@Autowired
	public ShoppingCartRestController(ShoppingCartService cartService,
			CustomerService customerService,
			AuthenticationControllerHelperUtil authenticationControllerHelperUtil) {
		super();
		this.cartService = cartService;
		this.authenticationControllerHelperUtil = authenticationControllerHelperUtil;
	}


	@PostMapping("/cart/add/{productId}/{quantity}")
	public String addProductToCart(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
		
		LOGGER.info("ShoppingCartRestController | addProductToCart is called");

		
		Customer customer = authenticationControllerHelperUtil.getAuthenticatedCustomer(request);
		
		if(customer != null) {
			LOGGER.info("ShoppingCartRestController | addProductToCart | customer : " + customer.toString());
			
			Integer updatedQuantity;
			try {
				updatedQuantity = cartService.addProduct(productId, quantity, customer);
			} catch (ShoppingCartException ex) {
				// TODO Auto-generated catch block
				return ex.getMessage();
			}
			
			LOGGER.info("ShoppingCartRestController | addProductToCart | updatedQuantity : " + updatedQuantity);
			
			return updatedQuantity + " mặt hàng của sản phẩm này đã được thêm vào giỏ hàng của bạn.";
		}else {
			return "Bạn phải đăng nhập để thêm sản phẩm này vào giỏ hàng.";
		}
		
	}

	
	@DeleteMapping("/cart/remove/{productId}")
	public String removeProduct(@PathVariable("productId") Integer productId,
			HttpServletRequest request) {
		
		LOGGER.info("ShoppingCartRestController | removeProduct is called");
		
		Customer customer = authenticationControllerHelperUtil.getAuthenticatedCustomer(request);
		
		if(customer != null) {
			
			LOGGER.info("ShoppingCartRestController | removeProduct | customer : " + customer.toString());
			
			cartService.removeProduct(productId, customer);
			
			return "Sản phẩm đã được gỡ bỏ khỏi giỏ hàng của bạn.";
		}else {
			return "Bạn phải đăng nhập để loại bỏ sản phẩm.";
		}
		
	}
	
	@PostMapping("/cart/update/{productId}/{quantity}")
	public String updateQuantity(@PathVariable("productId") Integer productId,
			@PathVariable("quantity") Integer quantity, HttpServletRequest request) {
		
		LOGGER.info("ShoppingCartRestController | updateQuantity is called");
		
		Customer customer = authenticationControllerHelperUtil.getAuthenticatedCustomer(request);
		
		if(customer != null) {
			LOGGER.info("ShoppingCartRestController | updateQuantity | customer : " + customer.toString());
			
			float subtotal = cartService.updateQuantity(productId, quantity, customer);
			
			LOGGER.info("ShoppingCartRestController | updateQuantity | subtotal : " + subtotal);

			return String.valueOf(subtotal);
		}else {
			return "Bạn phải đăng nhập để thay đổi số lượng sản phẩm.";
		}
		
	}
}
