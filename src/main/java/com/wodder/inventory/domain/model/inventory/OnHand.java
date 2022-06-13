package com.wodder.inventory.domain.model.inventory;

import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import java.math.BigDecimal;
import java.util.Objects;

public class OnHand {

  private final InventoryCount count;
  private final Price price;
  private final UnitOfMeasurement uom;


  private OnHand(InventoryCount count, Price price, UnitOfMeasurement uom) {
    this.count = count;
    this.price = price;
    this.uom = uom;
  }

  public static OnHand zero() {
    return new OnHand(
        InventoryCount.ofZero(),
        Price.ofZero(),
        UnitOfMeasurement.empty()
    );
  }

  public static OnHand from(InventoryCount count, Price price, UnitOfMeasurement uom) {
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
