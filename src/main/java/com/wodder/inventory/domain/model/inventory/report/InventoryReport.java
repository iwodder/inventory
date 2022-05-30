package com.wodder.inventory.domain.model.inventory.report;

import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.product.Category;
import java.time.LocalDate;

public class InventoryReport {
  private final LocalDate generationDate;
  private final Inventory start;
  private final Inventory end;

  private InventoryReport(Inventory start, Inventory end) {
    this.generationDate = LocalDate.now();
    this.start = start;
    this.end = end;
  }

  public static InventoryReport between(Inventory startingInventory, Inventory endingInventory) {
    if (startingInventory.getInventoryDate().isBefore(endingInventory.getInventoryDate())) {
      return new InventoryReport(startingInventory, endingInventory);
    } else {
      throw new IllegalArgumentException(
          "Can't create inventory report with improperly ordered dates");
    }
  }

  public LocalDate getStartDate() {
    return start.getInventoryDate();
  }

  public LocalDate getEndDate() {
    return end.getInventoryDate();
  }

  public LocalDate getGenerationDate() {
    return generationDate;
  }

  public Usage getUsage(Category category) {
    return new Usage();
  }
}
