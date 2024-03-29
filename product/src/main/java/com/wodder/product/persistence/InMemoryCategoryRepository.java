package com.wodder.product.persistence;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import com.wodder.product.domain.model.category.CategoryRepository;
import java.util.Optional;

@InMemory
public final class InMemoryCategoryRepository
    extends InMemoryRepository<Category, CategoryId> implements CategoryRepository {

  InMemoryCategoryRepository() {
  }

  @Override
  public Optional<Category> updateItem(Category item) {
    return Optional.empty();
  }

  @Override
  public Optional<Category> loadByName(String name) {
    return items.stream().filter(c -> c.getName().equals(name)).findAny();
  }
}
