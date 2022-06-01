package com.wodder.inventory.domain.model.inventory.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.inventory.domain.model.inventory.InventoryCount;
import com.wodder.inventory.domain.model.inventory.OnHand;
import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UsageTest {

  @Test
  @DisplayName("Should calculate the used number of units")
  void units() {
    Price p = new Price("1.0", "1.5");
    UnitOfMeasurement uom = new UnitOfMeasurement("Gallons", 4);
    OnHand start = OnHand.from(InventoryCount.countFrom("1", "2"), p, uom);
    OnHand end = OnHand.from(InventoryCount.countFrom("0", "2"), p, uom);

    Usage u = Usage.of(start, end);

    assertEquals(1, u.getUnits());
  }

  @Test
  @DisplayName("Should calculate the used number of dollars")
  void dollars() {
    Price p = new Price("1.0", "1.5");
    UnitOfMeasurement uom = new UnitOfMeasurement("Gallons", 4);
    OnHand start = OnHand.from(InventoryCount.countFrom("1", "2"), p, uom);
    OnHand end = OnHand.from(InventoryCount.countFrom("0", "2"), p, uom);

    Usage u = Usage.of(start, end);

    assertEquals(1.0, u.getDollars());
  }
}
