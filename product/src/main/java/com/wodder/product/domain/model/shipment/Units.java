package com.wodder.product.domain.model.shipment;

public class Units {

  private final String units;

  private Units(String units) {
    this.units = units;
  }

  public static Units of(String units) {
    return new Units(units);
  }

  public String getValue() {
    return units;
  }
}
