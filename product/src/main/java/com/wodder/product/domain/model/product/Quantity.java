package com.wodder.product.domain.model.product;

import java.util.Objects;

public class Quantity {
  private static final Quantity ZERO = new Quantity(0);
  private final int amt;

  private Quantity(int amt) {
    this.amt = amt;
  }

  public static Quantity of(String amount) {
    return new Quantity(Integer.parseInt(amount));
  }

  public static Quantity zero() {
    return ZERO;
  }

  public Quantity add(Quantity other) {
    return new Quantity(this.amt + other.amt);
  }

  public int getAmount() {
    return this.amt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Quantity quantity = (Quantity) o;
    return amt == quantity.amt;
  }

  @Override
  public int hashCode() {
    return Objects.hash(amt);
  }

  @Override
  public String toString() {
    return "Quantity{" +
        "amt=" + amt +
        '}';
  }
}
