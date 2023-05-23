package com.greenbookshop.service.impl;

import java.util.List;

import com.greenbookshop.common.entity.EmailSettingBag;
import com.greenbookshop.common.entity.setting.Setting;
import com.greenbookshop.setting.CurrencySettingBag;
import com.greenbookshop.setting.PaymentSettingBag;

public interface ISettingService {

	public List<Setting> getGeneralSettings();
	public EmailSettingBag getEmailSettings();
	public CurrencySettingBag getCurrencySettings();
	public String getCurrencyCode();
	public PaymentSettingBag getPaymentSettings();
}
