package com.wodder.product.application;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import com.wodder.product.domain.model.category.CategoryRepository;
import com.wodder.product.dto.CategoryDto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Inject
  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public CategoryDto createCategory(String name) {
    return categoryRepository
        .loadByName(name)
        .map(Category::toModel)
        .orElseGet(() -> categoryRepository.createItem(Category.of(name)).toModel());
  }

  @Override
  public Optional<CategoryDto> loadCategory(String id) {
    return categoryRepository.loadById(CategoryId.categoryIdOf(id)).map(Category::toModel);
  }

  @Override
  public List<CategoryDto> loadCategories() {
    return categoryRepository.loadAllItems().stream()
        .map(Category::toModel)
        .collect(Collectors.toList());
  }
}
