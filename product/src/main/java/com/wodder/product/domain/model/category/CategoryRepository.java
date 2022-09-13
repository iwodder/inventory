package com.wodder.product.domain.model.category;

import com.wodder.product.domain.model.Repository;
import java.util.Optional;

public interface CategoryRepository extends Repository<Category, CategoryId> {

  Optional<Category> loadByName(String name);
}
