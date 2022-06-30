package com.wodder.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountDto  {

  @JsonProperty
  private String itemId;
  @JsonProperty
  private String productId;
  @JsonProperty
  private double units;
  @JsonProperty
  private double cases;

  public CountDto(double units, double cases) {
    this.units = units;
    this.cases = cases;
  }

  public CountDto(String productId) {
    this.productId = productId;
    this.units = 0.0;
    this.cases = 0.0;
  }

  public CountDto(String productId, double units, double cases) {
    this.productId = productId;
    this.units = units;
    this.cases = cases;
  }

  public CountDto(String itemId, String productId, double units, double cases) {
    this.itemId = itemId;
    this.productId = productId;
    this.units = units;
    this.cases = cases;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getUnits() {
    return Double.toString(units);
  }

  public void setUnits(double units) {
    this.units = units;
  }

  public String getCases() {
    return Double.toString(cases);
  }

  public void setCases(double cases) {
    this.cases = cases;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }
}
