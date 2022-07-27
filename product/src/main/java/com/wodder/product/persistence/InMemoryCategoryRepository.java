package com.wodder.product.persistence;

import com.wodder.product.domain.model.product.Category;
import com.wodder.product.domain.model.product.CategoryId;
import java.util.Optional;

public final class InMemoryCategoryRepository extends InMemoryRepository<Category, CategoryId> {

  InMemoryCategoryRepository() {
  }

  @Override
  public Optional<Category> updateItem(Category item) {
    return Optional.empty();
  }
}
