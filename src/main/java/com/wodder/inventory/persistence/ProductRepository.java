package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.product.Product;
import java.util.List;

public interface ProductRepository {

  List<Product> loadActiveItems();
}
