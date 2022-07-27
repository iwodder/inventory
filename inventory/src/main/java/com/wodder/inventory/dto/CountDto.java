package com.wodder.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountDto  {

  @JsonProperty
  private String itemId;
  @JsonProperty
  private String productId;
  @JsonProperty
  private String units;
  @JsonProperty
  private String cases;

  public CountDto(String itemId, String productId, String units, String cases) {
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
    return units;
  }

  public void setUnits(String units) {
    this.units = units;
  }

  public String getCases() {
    return cases;
  }

  public void setCases(String cases) {
    this.cases = cases;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }
}
