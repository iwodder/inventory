package com.wodder.inventory.domain.model.inventory.report;

import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.domain.model.inventory.OnHand;
import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.dto.ReportDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class InventoryReport {
  private final LocalDate generationDate;
  private final Inventory start;
  private final Inventory end;

  private final Map<Item, Usage> usages = new HashMap<>();
  private final Set<ReceivedItem> items = new HashSet<>();

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

  public InventoryReport withReceivedItems(Iterable<ReceivedItem> items) {
    items.forEach(this.items::add);
    return this;
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
    return Usage.none();
  }

  public Usage getUsage(String itemName) {
    return usages.entrySet()
        .stream()
        .filter((e) -> e.getKey().getName().equals(itemName))
        .map(Entry::getValue)
        .findAny()
        .orElseGet(Usage::none);
  }

  public void process() {
    processEndingInventory();
    processStartingInventory();
    processReceivedItems();
  }

  private void processEndingInventory() {
    end.getItems().forEach((ending) -> {
      Item starting = start.getItem(ending.getName());
      usages.put(ending, Usage.of(starting.getOnHand(), ending.getOnHand()));
    });
  }

  private void processStartingInventory() {
    start.getItems().forEach((starting) -> {
      usages.computeIfAbsent(starting, (item) -> Usage.of(item.getOnHand(), OnHand.zero()));
    });
  }

  public void processReceivedItems() {
    items.forEach(
        (item) -> {
          for (var key : usages.keySet()) {
            if (key.getId().equals(item.getId())) {
              usages.compute(key, (k, v) -> v.addReceivedQty(item.getQuantity()));
            }
          }
        }
    );
  }

  public ReportDto toDto() {
    ReportDto dto = new ReportDto();
    dto.setGenerationDate(this.generationDate.format(DateTimeFormatter.ISO_DATE));
    dto.setStartingDate(this.getStartDate().format(DateTimeFormatter.ISO_DATE));
    dto.setEndingDate(this.getEndDate().format(DateTimeFormatter.ISO_DATE));
    usages.forEach((k, v) -> {
      dto.addUsage(k.toModel(), v.toDto());
    });
    return dto;
  }
}
