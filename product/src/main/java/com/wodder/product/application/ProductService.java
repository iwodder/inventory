package com.wodder.product.application;

import com.wodder.product.dto.ProductDto;
import java.util.List;
import java.util.Optional;

public interface ProductService {

  Optional<ProductDto> createNewProduct(ProductDto newItem);

  Optional<ProductDto> createNewProduct(
      String name,
      String category,
      String location,
      String unit,
      int unitsPerCase,
      String unitPrice,
      String casePrice);

  Boolean deleteProduct(String productId);

  Optional<ProductDto> updateProductCategory(String productId, String category);

  Optional<ProductDto> updateProductLocation(String productId, String location);

  Optional<ProductDto> updateProductName(String productId, String name);

  Optional<ProductDto> updateProductUnitOfMeasurement(
      String productId, String unitOfMeasurement, Integer unitsPerCase);

  Optional<ProductDto> updateProductPrice(String productId, String unitPrice, String casePrice);

  Optional<ProductDto> loadProduct(String productId);

  List<ProductDto> loadAllActiveProducts();
}
