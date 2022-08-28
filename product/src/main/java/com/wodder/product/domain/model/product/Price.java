package com.wodder.product.domain.model.product;

import java.math.BigDecimal;
import java.math.MathContext;

public class Price {
  private static final Price ZERO = new Price("0.00");
  private final BigDecimal value;

  public Price(String value) {
    this(new BigDecimal(value, new MathContext(4)));
  }

  public Price(BigDecimal value) {
    this.value = value;
  }

  public Price(Price that) {
    this(that.getValue());
  }

  public static Price ofZero() {
    return ZERO;
  }

  public static Price of(String value) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Price is required");
    }
    return new Price(value);
  }

  public BigDecimal getValue() {
    return value;
  }

  public String stringValue() {
    return value.toString();
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
    return value.equals(price.getValue());
  }

  @Override
  public int hashCode() {
    int result = getValue().hashCode();
    return 31 * result;
  }

  @Override
  public String toString() {
    return "Price{value=" + value + '}';
  }
}
