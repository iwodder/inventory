package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.product.Price;
import java.math.BigDecimal;
import java.util.Objects;

public class OnHand {

  private final Count count;
  private final Price price;
  private final Unit uom;


  private OnHand(Count count, Price price, Unit uom) {
    this.count = count;
    this.price = price;
    this.uom = uom;
  }

  public static OnHand zero() {
    return new OnHand(
        Count.ofZero(),
        Price.ofZero(),
        Unit.empty()
    );
  }

  public static OnHand from(Count count, Price price, Unit uom) {
    return new OnHand(count, price, uom);
  }

  public double getTotalDollars() {
    BigDecimal unitTotal = price.getUnitPrice().multiply(BigDecimal.valueOf(count.getUnits()));
    BigDecimal caseTotal = price.getCasePrice().multiply(BigDecimal.valueOf(count.getCases()));
    return unitTotal.add(caseTotal).doubleValue();
  }

  public double getUnitQty() {
    return count.getUnits();
  }

  public double getCaseQty() {
    return count.getCases();
  }

  public Price getPrice() {
    return price;
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
    return count.equals(onHand.count) && price.equals(onHand.price) && uom.equals(onHand.uom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, price, uom);
  }
}
