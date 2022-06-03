package com.wodder.inventory.domain.model.inventory.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.InventoryCount;
import com.wodder.inventory.domain.model.inventory.InventoryItem;
import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class InventoryReportTest {

  @Test
  @DisplayName("Should be able to create an inventory report between two dates")
  void betweenTwoDates() {
    InventoryReport report =
        InventoryReport.between(
            new Inventory(LocalDate.of(2022, 5, 1)),
            new Inventory(LocalDate.of(2022, 5, 2)));
    assertNotNull(report);
  }

  @Test
  @DisplayName("Should have dates that match inventory")
  void startingDate() {
    LocalDate start = LocalDate.of(2022, 5, 1);
    LocalDate end = LocalDate.of(2022, 5, 2);
    InventoryReport report =
        InventoryReport.between(
            new Inventory(start),
            new Inventory(end));

    assertEquals(start, report.getStartDate());
    assertEquals(end, report.getEndDate());
  }

  @Test
  @DisplayName("Should include the date of report generation")
  void generationDate() {
    LocalDate start = LocalDate.of(2022, 5, 1);
    LocalDate end = LocalDate.of(2022, 5, 2);
    InventoryReport report =
        InventoryReport.between(
            new Inventory(start),
            new Inventory(end));

    assertEquals(LocalDate.now(), report.getGenerationDate());
  }

  @ParameterizedTest
  @MethodSource("illegalInventoryArgs")
  @DisplayName("Ending inventory should be after starting inventory")
  void illegalInventories(Inventory start, Inventory end) {
    IllegalArgumentException e =
        assertThrows(IllegalArgumentException.class, () -> InventoryReport.between(start, end));
    assertTrue(e.getMessage().contains("Can't create inventory report with improperly ordered dates"));
  }

  @Test
  @DisplayName("Usage for non-present category should be zero")
  void categoryUsage() {
    LocalDate start = LocalDate.of(2022, 5, 1);
    LocalDate end = LocalDate.of(2022, 5, 2);
    InventoryReport report =
        InventoryReport.between(
            new Inventory(start),
            new Inventory(end));

    Usage usage = report.getUsage(Category.of("frozen"));

    assertNotNull(usage);
    assertEquals(Usage.none(), usage);
  }

  @Test
  @Disabled
  @DisplayName("Usage for present category should have values")
  void presentCategoryUsage() {
    InventoryItem sampleItem = new InventoryItem(
        "Cheese", "Refrigerator", "Dairy",
        new UnitOfMeasurement("Ounces", 4),
        new Price("0.98", "3.96"));
    Inventory start = new Inventory(LocalDate.of(2022, 5, 1));
    start.addItemToInventory(sampleItem);
    start.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("1.0", "0.0"));
    Inventory end = new Inventory(LocalDate.of(2022, 5, 2));
    end.addItemToInventory(sampleItem);
    end.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("0.5", "0.0"));

    InventoryReport report =
        InventoryReport.between(
            new Inventory(start),
            new Inventory(end));

    Usage usage = report.getUsage(Category.of("dairy"));

    assertEquals(0.5, usage.getUnits(), 0.00);
    assertEquals(0.49, usage.getDollars(), 0.00);
  }

  @Test
  @DisplayName("Should be able to return the usage for an item")
  void itemUsage() {
    InventoryItem sampleItem = new InventoryItem(
        "Cheese", "Refrigerator", "Dairy",
        new UnitOfMeasurement("Ounces", 4),
        new Price("0.98", "3.96"));
    Inventory start = new Inventory(LocalDate.of(2022, 5, 1));
    start.addItemToInventory(sampleItem);
    start.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("1.0", "0.0"));
    Inventory end = new Inventory(LocalDate.of(2022, 5, 2));
    end.addItemToInventory(sampleItem);
    end.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("0.5", "0.0"));

    InventoryReport report =
        InventoryReport.between(
            new Inventory(start),
            new Inventory(end));
    report.process();

    Usage usage = report.getUsage("Cheese");
    assertEquals(0.5, usage.getUnits(), 0.00);
    assertEquals(0.49, usage.getDollars(), 0.00);
  }

  @Test
  @DisplayName("Should return negative usage when item is absent from starting inventory")
  void absentItemUsage() {
    InventoryItem sampleItem = new InventoryItem(
        "Cheese", "Refrigerator", "Dairy",
        new UnitOfMeasurement("Ounces", 4),
        new Price("0.98", "3.96"));
    Inventory start = new Inventory(LocalDate.of(2022, 5, 1));
    Inventory end = new Inventory(LocalDate.of(2022, 5, 2));
    end.addItemToInventory(sampleItem);
    end.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("0.5", "0.0"));

    InventoryReport report = InventoryReport.between(new Inventory(start), new Inventory(end));
    report.process();

    Usage usage = report.getUsage("Cheese");
    assertEquals(-0.5, usage.getUnits(), 0.00);
    assertEquals(-0.49, usage.getDollars(), 0.00);
  }

  @Test
  @DisplayName("Should return usage of none for an absent item")
  void absentStartingItemUsage() {
    Inventory start = new Inventory(LocalDate.of(2022, 5, 1));
    Inventory end = new Inventory(LocalDate.of(2022, 5, 2));

    InventoryReport report = InventoryReport.between(new Inventory(start), new Inventory(end));
    report.process();

    Usage usage = report.getUsage("Cheese");
    assertEquals(0.0, usage.getUnits(), 0.00);
    assertEquals(0.0, usage.getDollars(), 0.00);
  }

  @Test
  @DisplayName("Should return 100% usage when item is absent from ending inventory")
  void absentEndingItemUsage() {
    InventoryItem sampleItem = new InventoryItem(
        "Cheese", "Refrigerator", "Dairy",
        new UnitOfMeasurement("Ounces", 4),
        new Price("0.98", "3.96"));
    Inventory start = new Inventory(LocalDate.of(2022, 5, 1));
    start.addItemToInventory(sampleItem);
    start.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("0.5", "1.0"));

    Inventory end = new Inventory(LocalDate.of(2022, 5, 2));

    InventoryReport report = InventoryReport.between(new Inventory(start), new Inventory(end));
    report.process();

    Usage usage = report.getUsage("Cheese");
    assertEquals(0.5, usage.getUnits(), 0.00);
    assertEquals(4.45, usage.getDollars(), 0.00);
  }

  static Stream<Arguments> illegalInventoryArgs() {
    return Stream.of(
        Arguments.of(
            new Inventory(LocalDate.of(2022, 5, 1)),
            new Inventory(LocalDate.of(2022, 4, 30))),
        Arguments.of(
            new Inventory(LocalDate.of(2022, 5, 1)),
            new Inventory(LocalDate.of(2022, 5, 1)))
    );
  }
}
