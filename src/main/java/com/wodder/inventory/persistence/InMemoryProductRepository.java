package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.product.Product;
import com.wodder.inventory.domain.model.product.ProductId;
import java.util.List;
import java.util.stream.Collectors;

final class InMemoryProductRepository extends InMemoryRepository<Product, ProductId>
    implements ProductRepository {

  InMemoryProductRepository() {
  }

  @Override
  public List<Product> loadActiveItems() {
    return items.stream().filter(Product::isActive).map(Product::new).collect(Collectors.toList());
  }
}
