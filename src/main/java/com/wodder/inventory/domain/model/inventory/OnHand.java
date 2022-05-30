package com.wodder.inventory.domain.model.inventory;

import java.util.Objects;

public class OnHand {

  private double unitQty;
  private double caseQty;
  private double unitPrice;
  private double casePrice;

  private OnHand(double unitQty, double caseQty, double unitPrice, double casePrice) {
    setUnitQty(unitQty);
    setCaseQty(caseQty);
    setUnitPrice(unitPrice);
    setCasePrice(casePrice);
  }

  public static OnHand zero() {
    return new OnHand(0, 0, 0, 0);
  }

  public static OnHand of(double unitQty, double caseQty, double unitPrice, double casePrice) {
    return new OnHand(unitQty, caseQty, unitPrice, casePrice);
  }

  public double getTotalDollars() {
    return 0;
  }

  public double getUnitQty() {
    return unitQty;
  }

  public double getCaseQty() {
    return caseQty;
  }

  private void setUnitQty(double value) {
    if (isNonNegative(value)) {
      this.unitQty = value;
    } else {
      throwException("Unit quantity");
    }
  }

  private void setCaseQty(double value) {
    if (isNonNegative(value)) {
      this.caseQty = value;
    } else {
      throwException("Case quantity");
    }
  }

  private void setUnitPrice(double value) {
    if (isNonNegative(value)) {
      this.unitPrice = value;
    } else {
      throwException("Unit price");
    }
  }

  private void setCasePrice(double value) {
    if (isNonNegative(value)) {
      this.casePrice = value;
    } else {
      throwException("Case price");
    }
  }

  private boolean isNonNegative(double value) {
    return Double.compare(0, value) <= 0;
  }

  private void throwException(String value) {
    throw new IllegalArgumentException(String.format("%s can't be negative", value));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OnHand onHand = (OnHand) o;
    return Double.compare(onHand.getUnitQty(), getUnitQty()) == 0
        && Double.compare(onHand.getCaseQty(), getCaseQty()) == 0
        && Double.compare(onHand.unitPrice, unitPrice) == 0
        && Double.compare(onHand.casePrice, casePrice) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getUnitQty(), getCaseQty(), unitPrice, casePrice);
  }
}
