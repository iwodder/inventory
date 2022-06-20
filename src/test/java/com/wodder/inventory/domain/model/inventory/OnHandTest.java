package com.wodder.inventory.domain.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
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
    Count c = Count.countFrom("1", "2");
    Price p = new Price("1.0", "1.49");
    UnitOfMeasurement unitOfMeasurement = new UnitOfMeasurement("Gallons", 4);

    OnHand onHand = OnHand.from(c, p, unitOfMeasurement);

    assertEquals(3.98, onHand.getTotalDollars());
  }
}
