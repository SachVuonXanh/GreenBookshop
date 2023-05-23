package com.greenbookshop.service.impl;

import java.util.List;

import com.greenbookshop.common.entity.Category;
import com.greenbookshop.common.exception.CategoryNotFoundException;

public interface ICategoryService {

	public List<Category> listNoChildrenCategories();
	
	public Category getCategory(String alias) throws CategoryNotFoundException;
	
	public List<Category> getCategoryParents(Category child);
}
