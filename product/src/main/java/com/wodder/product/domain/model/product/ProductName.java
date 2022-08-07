package com.wodder.product.domain.model.product;

import java.util.Objects;

public class ProductName {
  private final String name;

  private ProductName(String name) {
    this.name = name;
  }

  public static ProductName of(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException(name == null ? "Name was null" : "Name was blank");
    }
    return new ProductName(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductName that = (ProductName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "ProductName{" +
        "name='" + name + '\'' +
        '}';
  }

  public String getValue() {
    return this.name;
  }
}
