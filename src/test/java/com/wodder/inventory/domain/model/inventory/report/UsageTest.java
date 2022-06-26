package com.wodder.inventory.domain.model.inventory.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.inventory.domain.model.inventory.Count;
import com.wodder.inventory.domain.model.inventory.OnHand;
import com.wodder.inventory.domain.model.inventory.Unit;
import com.wodder.inventory.domain.model.product.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UsageTest {

  @Test
  @DisplayName("Should calculate the used number of units")
  void units() {
    Price p = new Price("1.0", "1.5");
    Unit uom = Unit.of("Gallons");
    OnHand start = OnHand.from(Count.countOf("1", "2"), p, uom);
    OnHand end = OnHand.from(Count.countOf("0", "2"), p, uom);

    Usage u = Usage.of(start, end);

    assertEquals(1, u.getUnits());
  }

  @Test
  @DisplayName("Should calculate the used number of dollars")
  void dollars() {
    Price p = new Price("1.0", "1.5");
    Unit uom = Unit.of("Gallons");
    OnHand start = OnHand.from(Count.countOf("1", "2"), p, uom);
    OnHand end = OnHand.from(Count.countOf("0", "2"), p, uom);

    Usage u = Usage.of(start, end);

    assertEquals(1.0, u.getDollars());
  }

  @Test
  @DisplayName("Should be able to add number of received units")
  void addUnits() {
    Price p = new Price("1.0", "1.5");
    Unit uom = Unit.of("Gallons");
    OnHand start = OnHand.from(Count.countOf("1", "2"), p, uom);
    OnHand end = OnHand.from(Count.countOf("0", "2"), p, uom);

    Usage u = Usage.of(start, end);
    Usage u1 = u.addReceivedQty(1);

    assertEquals(2.0, u1.getUnits());
    assertEquals(1.0, u.getUnits());
  }
}
