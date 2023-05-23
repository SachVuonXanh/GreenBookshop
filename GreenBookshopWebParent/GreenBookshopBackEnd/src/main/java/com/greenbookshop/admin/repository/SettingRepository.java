package com.greenbookshop.admin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.greenbookshop.common.entity.setting.Setting;
import com.greenbookshop.common.entity.setting.SettingCategory;

public interface SettingRepository extends CrudRepository<Setting, String> {

	public List<Setting> findByCategory(SettingCategory category);
}
