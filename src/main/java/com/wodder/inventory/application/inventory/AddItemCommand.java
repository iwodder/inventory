package com.wodder.inventory.application.inventory;

public class AddItemCommand {

  private String productId;
  private String name;
  private String location;
  private String measurementUnit;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getMeasurementUnit() {
    return measurementUnit;
  }

  public void setMeasurementUnit(String measurementUnit) {
    this.measurementUnit = measurementUnit;
  }

  @Override
  public String toString() {
    return "AddItemCommand{"
        + "productId='" + productId + '\''
        + ", name='" + name + '\''
        + ", location='" + location + '\''
        + ", measurementUnit='" + measurementUnit + '\''
        + '}';
  }
}
