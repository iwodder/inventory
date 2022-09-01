package com.wodder.product.persistence;

import com.wodder.product.domain.model.product.ExternalId;
import com.wodder.product.domain.model.product.Product;
import com.wodder.product.domain.model.product.ProductId;
import com.wodder.product.domain.model.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final class InMemoryProductRepository extends InMemoryRepository<Product, ProductId>
    implements ProductRepository {

  InMemoryProductRepository() {
  }

  @Override
  public List<Product> loadActiveItems() {
    return items.stream().filter(Product::isActive).map(Product::new).collect(Collectors.toList());
  }

  @Override
  public Optional<Product> loadByExternalId(ExternalId id) {
    return items.stream().filter(p -> p.getExternalId().equals(id)).findFirst();
  }
}
