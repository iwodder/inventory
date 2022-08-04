package com.wodder.product.application;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import com.wodder.product.dto.CategoryModel;
import com.wodder.product.persistence.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {

  private final Repository<Category, CategoryId> categoryRepository;

  CategoryServiceImpl(Repository<Category, CategoryId> categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Optional<CategoryModel> createCategory(String name) {
    if (categoryRepository.loadByItem(new Category(name)).isEmpty()) {
      Category c = categoryRepository.createItem(new Category(name));
      return c == null ? Optional.empty() : Optional.of(c.toModel());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<CategoryModel> loadCategory(String id) {
    return categoryRepository.loadById(CategoryId.categoryIdOf(id)).map(Category::toModel);
  }

  @Override
  public List<CategoryModel> loadCategories() {
    return categoryRepository.loadAllItems().stream()
        .map(Category::toModel)
        .collect(Collectors.toList());
  }
}
