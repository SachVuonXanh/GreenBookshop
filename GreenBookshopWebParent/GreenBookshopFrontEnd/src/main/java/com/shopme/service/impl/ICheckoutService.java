package com.greenbookshop.service.impl;

import java.util.List;

import com.greenbookshop.checkout.CheckoutInfo;
import com.greenbookshop.common.entity.CartItem;
import com.greenbookshop.common.entity.ShippingRate;

public interface ICheckoutService {
	public CheckoutInfo prepareCheckout(List<CartItem> cartItems, ShippingRate shippingRate);
}
