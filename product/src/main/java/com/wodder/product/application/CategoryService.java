package com.wodder.product.application;

import com.wodder.product.dto.CategoryModel;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

  Optional<CategoryModel> createCategory(String name);

  Optional<CategoryModel> loadCategory(String id);

  List<CategoryModel> loadCategories();
}
