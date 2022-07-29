package com.wodder.product.application;

import com.wodder.product.domain.model.product.Category;
import com.wodder.product.domain.model.product.CategoryId;
import com.wodder.product.domain.model.product.Price;
import com.wodder.product.domain.model.product.Product;
import com.wodder.product.domain.model.product.ProductId;
import com.wodder.product.domain.model.product.UnitOfMeasurement;
import com.wodder.product.dto.ProductDto;
import com.wodder.product.persistence.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class ProductServiceImpl implements ProductService {

  private final Repository<Product, ProductId> productRepository;
  private final Repository<Category, CategoryId> categoryRepository;

  ProductServiceImpl(
      Repository<Product, ProductId> productRepository,
      Repository<Category, CategoryId> categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Optional<ProductDto> createNewProduct(ProductDto newItem) {
    return createNewProduct(
        newItem.getName(),
        newItem.getCategory(),
        newItem.getUnits(),
        newItem.getUnitsPerCase(),
        newItem.getItemPrice(),
        newItem.getCasePrice());
  }

  @Override
  public Optional<ProductDto> createNewProduct(
      String name,
      String category,
      String unit,
      int unitsPerCase,
      String unitPrice,
      String casePrice) {
    Category cat =
        categoryRepository
            .loadByItem(new Category(category))
            .orElseGet(() -> categoryRepository.createItem(new Category(category)));

    Product item =
        new Product(
            name,
            cat,
            new UnitOfMeasurement(unit, unitsPerCase),
            new Price(unitPrice, casePrice));

    return Optional.of(productRepository.createItem(item)).map(Product::toItemModel);
  }

  @Override
  public Boolean deleteProduct(String productId) {
    return productRepository.deleteItem(ProductId.productIdOf(productId));
  }

  @Override
  public Optional<ProductDto> updateProductCategory(String productId, String category) {
    if (productId == null) {
      return Optional.empty();
    } else {
      return processCategoryUpdate(productId, category);
    }
  }

  @Override
  public Optional<ProductDto> updateProductName(String productId, String name) {
    Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));
    if (opt.isPresent()) {
      Product item = opt.get();
      item.updateName(name);
      productRepository.updateItem(item);
      return Optional.of(item.toItemModel());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ProductDto> updateProductUnitOfMeasurement(
      String productId, String unitOfMeasurement, Integer unitsPerCase) {
    Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));
    ;
    if (opt.isPresent()) {
      Product item = opt.get();
      item.updateUnitOfMeasurement(new UnitOfMeasurement(unitOfMeasurement, unitsPerCase));
      productRepository.updateItem(item);
      return Optional.of(item.toItemModel());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ProductDto> updateProductPrice(
      String productId, String unitPrice, String casePrice) {
    Optional<Product> opt = productRepository.loadById(ProductId.productIdOf(productId));
    if (opt.isPresent()) {
      Product i = opt.get();
      i.updatePrice(new Price(unitPrice, casePrice));
      productRepository.updateItem(i);
      return Optional.of(i.toItemModel());
    } else {
      return Optional.empty();
    }
  }

  private Optional<ProductDto> processCategoryUpdate(String inventoryItemId, String category) {
    Optional<Product> inventoryItem =
        productRepository.loadById(ProductId.productIdOf(inventoryItemId));
    Optional<Category> c = categoryRepository.loadByItem(new Category(category));
    if (inventoryItem.isPresent() && c.isPresent()) {
      Product item = inventoryItem.get();
      item.updateCategory(c.get());
      return productRepository.updateItem(item).map(Product::toItemModel);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Optional<ProductDto> loadProduct(String productId) {
    if (productId == null) {
      return Optional.empty();
    }
    return productRepository.loadById(ProductId.productIdOf(productId)).map(Product::toItemModel);
  }

  @Override
  public List<ProductDto> loadAllActiveProducts() {
    return productRepository.loadAllItems().stream()
        .map(Product::toItemModel)
        .collect(Collectors.toList());
  }
}
