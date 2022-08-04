package com.wodder.product.persistence;

import com.wodder.product.domain.model.product.ExternalId;
import com.wodder.product.domain.model.product.Product;
import com.wodder.product.domain.model.product.ProductId;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Repository<Product, ProductId> {

  List<Product> loadActiveItems();

  Optional<Product> loadByExternalId(ExternalId id);
}
