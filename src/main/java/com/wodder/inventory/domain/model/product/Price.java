package com.wodder.inventory.domain.model.product;

import java.math.BigDecimal;

public class Price {
  private static final Price ZERO = new Price("0", "0");
  private final BigDecimal unitPrice;
  private final BigDecimal casePrice;

  public Price(String unitPrice, String casePrice) {
    this(new BigDecimal(unitPrice), new BigDecimal(casePrice));
  }

  public Price(BigDecimal unitPrice, BigDecimal casePrice) {
    this.unitPrice = unitPrice;
    this.casePrice = casePrice;
  }

  public Price(BigDecimal unitPrice) {
    this(unitPrice, null);
  }

  public Price(Price that) {
    this(that.getUnitPrice(), that.getCasePrice());
  }

  public static Price ofZero() {
    return ZERO;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public BigDecimal getCasePrice() {
    return casePrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Price price = (Price) o;
    if (!getUnitPrice().equals(price.getUnitPrice())) {
      return false;
    }
    return getCasePrice().equals(price.getCasePrice());
  }

  @Override
  public int hashCode() {
    int result = getUnitPrice().hashCode();
    result = 31 * result + getCasePrice().hashCode();
    return result;
  }

  @Override
  public String toString() {
    if (casePrice != null) {
      return String.format("Item Price=$%.2f, Case Price=$%.2f", unitPrice, casePrice);
    } else {
      return String.format("Item Price=$%.2f, Case Price=N/A", unitPrice);
    }
  }
}
