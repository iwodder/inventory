package com.wodder.inventory.application;

import com.wodder.inventory.dto.*;

import java.util.*;

public interface CategoryService {

	Optional<CategoryModel> createCategory(String name);

	Optional<CategoryModel> loadCategory(String id);

	List<CategoryModel> loadCategories();

}
