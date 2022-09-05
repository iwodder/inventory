package com.wodder.product.application;

import com.wodder.product.dto.CategoryDto;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

  CategoryDto createCategory(String name);

  Optional<CategoryDto> loadCategory(String id);

  List<CategoryDto> loadCategories();
}
