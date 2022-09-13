package com.wodder.product.application;

public class UpdateProductPriceCommand {

  private String itemPrice;
  private String casePrice;

  public void setItemPrice(String itemPrice) {
    this.itemPrice = itemPrice;
  }

  public String getItemPrice() {
    return itemPrice;
  }

  public void setCasePrice(String casePrice) {
    this.casePrice = casePrice;
  }

  public String getCasePrice() {
    return casePrice;
  }

  @Override
  public String toString() {
    return "UpdateProductPriceCommand{"
        + "itemPrice='" + itemPrice + '\''
        + ", casePrice='" + casePrice + '\''
        + '}';
  }
}
