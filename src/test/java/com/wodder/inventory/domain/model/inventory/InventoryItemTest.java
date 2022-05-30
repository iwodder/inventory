package com.wodder.inventory.domain.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InventoryItemTest {

  @Test
  @Disabled
  @DisplayName("Should return a value of the item")
  void value() {
   InventoryItem item = new InventoryItem(
       "Cheese", "Refrigerator", "Dairy",
       new UnitOfMeasurement("ounce", 16),
       new Price("0.98", "10.00"),
       InventoryCount.countFrom("2", "1")
   );

   assertEquals(11.96, item.getOnHand().getTotalDollars());
   assertEquals(18, item.getOnHand().getUnitQty());
   assertEquals(1.125, item.getOnHand().getCaseQty());
  }

  @Test
  @DisplayName("Should have nothing on hand when counts are zero")
  void zeroCounts() {
    InventoryItem item = new InventoryItem(
        "Cheese", "Refrigerator", "Dairy",
        new UnitOfMeasurement("ounce", 16),
        new Price("0.98", "10.00"),
        InventoryCount.countOfZero()
    );

    assertEquals(OnHand.zero(), item.getOnHand());
  }
}
