package com.wodder.product.domain.model.shipment;

public class CasePack {

  private String pack;

  private CasePack(String pack) {
    this.pack = pack;
  }

  public static CasePack of(String pack) {
    return new CasePack(pack);
  }

  public String getValue() {
    return pack;
  }
}
