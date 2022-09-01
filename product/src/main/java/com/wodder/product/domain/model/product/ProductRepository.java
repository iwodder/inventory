package com.wodder.product.domain.model.product;

import com.wodder.product.domain.model.Repository;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Repository<Product, ProductId> {

  List<Product> loadActiveItems();

  Optional<Product> loadByExternalId(ExternalId id);
}
