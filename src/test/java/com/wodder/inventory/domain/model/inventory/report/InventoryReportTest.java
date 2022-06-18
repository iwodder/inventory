package com.wodder.inventory.domain.model.inventory.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.domain.model.inventory.InventoryCount;
import com.wodder.inventory.domain.model.inventory.InventoryLocation;
import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.domain.model.inventory.ItemId;
import com.wodder.inventory.domain.model.product.Category;
import com.wodder.inventory.domain.model.product.Price;
import com.wodder.inventory.domain.model.product.UnitOfMeasurement;
import com.wodder.inventory.dto.ReportDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;
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
  @DisplayName("Should be able to return the usage for an item")
  void itemUsage() {
    Item sampleItem = new Item(
        "Cheese", InventoryLocation.of("Refrigerator"),
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
    Item sampleItem = new Item(
        "Cheese", InventoryLocation.of("Refrigerator"),
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
    Item sampleItem = new Item(
        "Cheese", InventoryLocation.of("Refrigerator"),
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

  @Test
  @DisplayName("Should return no usage when starting on hand matches ending on hand")
  void noUsage() {
    Item sampleItem = new Item(
        "Cheese", InventoryLocation.of("Refrigerator"),
        new UnitOfMeasurement("Ounces", 4),
        new Price("0.98", "3.96"));
    Inventory start = new Inventory(LocalDate.of(2022, 5, 1));
    start.addItemToInventory(sampleItem);
    start.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("0.5", "1.0"));

    Inventory end = new Inventory(LocalDate.of(2022, 5, 2));
    end.addItemToInventory(sampleItem);
    end.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("0.5", "1.0"));

    InventoryReport report = InventoryReport.between(new Inventory(start), new Inventory(end));
    report.process();

    Usage usage = report.getUsage("Cheese");
    assertEquals(0.0, usage.getUnits(), 0.00);
    assertEquals(0.0, usage.getDollars(), 0.00);
  }

  @Test
  @DisplayName("Should be able to add no received items report")
  void noReceived() {
    Item sampleItem = new Item(
        "Cheese", InventoryLocation.of("Refrigerator"),
        new UnitOfMeasurement("Ounces", 4),
        new Price("0.98", "3.96"));
    Inventory start = new Inventory(LocalDate.of(2022, 5, 1));
    start.addItemToInventory(sampleItem);
    start.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("0.5", "1.0"));

    Inventory end = new Inventory(LocalDate.of(2022, 5, 2));
    end.addItemToInventory(sampleItem);
    end.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("0.5", "1.0"));

    InventoryReport report =
        InventoryReport.between(new Inventory(start), new Inventory(end))
            .withReceivedItems(new ArrayList<>());
    report.process();

    Usage usage = report.getUsage("Cheese");
    assertEquals(0.0, usage.getUnits(), 0.00);
    assertEquals(0.0, usage.getDollars(), 0.00);
  }

  @Test
  @DisplayName("Should be able to add a received items report")
  void received() {
    Item sampleItem = new Item(
        ItemId.of("123"),
        "Cheese", InventoryLocation.of("Refrigerator"),
        new UnitOfMeasurement("Ounces", 4),
        new Price("0.98", "3.96"), InventoryCount.ofZero());
    Inventory start = new Inventory(LocalDate.of(2022, 5, 1));
    start.addItemToInventory(sampleItem);
    start.updateInventoryCount("Cheese", "Refrigerator",
        InventoryCount.countFrom("0.5", "1.0"));

    Inventory end = new Inventory(LocalDate.of(2022, 5, 2));
    end.addItemToInventory(sampleItem);
    end.updateInventoryCount("Cheese","Refrigerator",
        InventoryCount.countFrom("0.5", "1.0"));

    InventoryReport report =
        InventoryReport.between(start, end)
            .withReceivedItems(Collections.singletonList(
                ReceivedItem.of(ItemId.of("123"), 2)
            ));
    report.process();

    Usage usage = report.getUsage("Cheese");
    assertEquals(2, usage.getUnits(), 0.00);
    assertEquals(1.96, usage.getDollars(), 0.00);
  }

  @Test
  @DisplayName("Converted DTO should have correct dates")
  void toDto() {
    Item sampleItem = new Item(
        "Cheese", InventoryLocation.of("Refrigerator"),
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
    ReportDto dto = report.toDto();

    assertNotEquals(
        "",
        dto.getGenerationDate(),
        "Generation date shouldn't be blank");
    assertEquals("2022-05-01", dto.getStartDate());
    assertEquals("2022-05-02", dto.getEndDate());
  }

  @Test
  @DisplayName("Converted DTO should have correct number of usages")
  void toDtoItem() {
    Item sampleItem = new Item(
        "Cheese", InventoryLocation.of("Refrigerator"),
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
    ReportDto dto = report.toDto();

    assertEquals(1, dto.getUsageCnt());
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
