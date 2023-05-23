package com.greenbookshop.admin.service.impl;

import java.util.List;

import com.greenbookshop.admin.util.GeneralSettingBag;
import com.greenbookshop.common.entity.setting.Setting;

public interface ISettingService {

	public List<Setting> listAllSettings();
	
	public GeneralSettingBag getGeneralSettings();
	
	public void saveAll(Iterable<Setting> settings);
	
	public List<Setting> getMailServerSettings();
	
	public List<Setting> getMailTemplateSettings();

	public List<Setting> getCurrencySettings();

	public List<Setting> getPaymentSettings();
}
