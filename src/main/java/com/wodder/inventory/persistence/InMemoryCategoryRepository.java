package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.domain.model.product.CategoryId;
import java.util.Optional;

public final class InMemoryCategoryRepository extends InMemoryRepository<Category, CategoryId> {

  InMemoryCategoryRepository() {
  }

  @Override
  public Optional<Category> updateItem(Category item) {
    return Optional.empty();
  }
}
