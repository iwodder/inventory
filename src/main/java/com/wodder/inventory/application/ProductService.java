package com.wodder.inventory.application;

import com.wodder.inventory.dto.ProductModel;
import java.util.List;
import java.util.Optional;

public interface ProductService {

  Optional<ProductModel> createNewProduct(ProductModel newItem);

  Optional<ProductModel> createNewProduct(
      String name,
      String category,
      String location,
      String unit,
      int unitsPerCase,
      String unitPrice,
      String casePrice);

  Boolean deleteProduct(String productId);

  Optional<ProductModel> updateProductCategory(String productId, String category);

  Optional<ProductModel> updateProductLocation(String productId, String location);

  Optional<ProductModel> updateProductName(String productId, String name);

  Optional<ProductModel> updateProductUnitOfMeasurement(
      String productId, String unitOfMeasurement, Integer unitsPerCase);

  Optional<ProductModel> updateProductPrice(String productId, String unitPrice, String casePrice);

  Optional<ProductModel> loadProduct(String productId);

  List<ProductModel> loadAllActiveProducts();
}
