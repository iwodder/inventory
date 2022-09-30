package com.wodder.product.domain.model.product;

public class ProductCreated {
  private final ProductId productId;
  private final ProductName name;
  private final UnitOfMeasurement unitOfMeasurement;

  public ProductCreated(ProductId id, ProductName name, UnitOfMeasurement units) {
    this.productId = id;
    this.name = name;
    this.unitOfMeasurement = units;
  }

  public ProductId getProductId() {
    return productId;
  }

  public ProductName getName() {
    return name;
  }

  public UnitOfMeasurement getUnitOfMeasurement() {
    return unitOfMeasurement;
  }

  @Override
  public String toString() {
    return "ProductCreated{productId="
        + productId + ", name="
        + name + ", unitOfMeasurement="
        + unitOfMeasurement + '}';
  }
}
