package com.wodder.inventory.domain.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.inventory.domain.model.product.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OnHandTest {

  @Test
  @DisplayName("Should be able to create an on-hand of zero")
  void zero() {
    OnHand onHand = OnHand.zero();
    assertEquals(0.0, onHand.getTotalDollars());
    assertEquals(0, onHand.getCaseQty());
    assertEquals(0, onHand.getUnitQty());
  }

  @Test
  @DisplayName("Should calculate the total dollars of an item")
  void dollars() {
    Count c = Count.countOf("1", "2");
    Price p = new Price("1.0", "1.49");
    Unit unit = Unit.of("Gallons");

    OnHand onHand = OnHand.from(c, p, unit);

    assertEquals(3.98, onHand.getTotalDollars());
  }
}
