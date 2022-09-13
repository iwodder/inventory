package com.wodder.product.application;

public class UpdateProductNameCommand {
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "UpdateProductNameCommand{name='" + name + '\'' + '}';
  }
}
