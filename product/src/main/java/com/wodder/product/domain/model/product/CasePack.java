package com.wodder.product.domain.model.product;

import java.util.Objects;

public class CasePack {
  private final Integer itemsPerCase;

  private CasePack(String number) {
    this.itemsPerCase = Integer.parseInt(number);
  }

  public static CasePack ofItemsPerCase(String number) {
    return new CasePack(number);
  }

  public Integer getValue() {
    return itemsPerCase;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CasePack casePack = (CasePack) o;
    return itemsPerCase.equals(casePack.itemsPerCase);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemsPerCase);
  }

  @Override
  public String toString() {
    return "CasePack{itemsPerCase=" + itemsPerCase + '}';
  }
}
