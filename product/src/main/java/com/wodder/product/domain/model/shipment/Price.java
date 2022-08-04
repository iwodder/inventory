package com.wodder.product.domain.model.shipment;

public class Price {
  private final String itemPrice;
  private final String casePrice;

  private Price(String itemPrice, String casePrice) {
    this.itemPrice = itemPrice;
    this.casePrice = casePrice;
  }

  public static Price of(String itemPrice, String casePrice) {
    return new Price(itemPrice, casePrice);
  }

  public String getItemPrice() {
    return itemPrice;
  }

  public String getCasePrice() {
    return casePrice;
  }
}
