package com.wodder.product.application;

import com.wodder.product.dto.ProductDto;
import java.util.List;
import java.util.Optional;

public interface ProductService {

  Optional<ProductDto> createNewProduct(ProductDto newItem);

  Optional<ProductDto> createNewProduct(
      String name,
      String category,
      String unit,
      int unitsPerCase,
      String unitPrice,
      String casePrice);

  String createProduct(CreateProductCommand cmd);

  Boolean deleteProduct(String productId);

  Optional<ProductDto> updateProductCategory(String productId, String category);

  Optional<ProductDto> updateProductName(String productId, String name);

  Optional<ProductDto> updateProductUnitOfMeasurement(
      String productId, String unitOfMeasurement);

  Optional<ProductDto> updateProductPrice(String productId, String unitPrice, String casePrice);

  Optional<ProductDto> loadProductById(String productId);

  Optional<ProductDto> loadProductByExternalId(String externalId);

  List<ProductDto> loadAllActiveProducts();

  Boolean receiveShipmentOfProducts(ReceiveShipmentCommand cmd);
}
