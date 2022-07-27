package com.wodder.product.persistence;

import com.wodder.product.domain.model.product.Product;
import java.util.List;

public interface ProductRepository {

  List<Product> loadActiveItems();
}
