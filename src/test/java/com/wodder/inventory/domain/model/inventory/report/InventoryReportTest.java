package com.wodder.inventory.domain.model.inventory.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.domain.model.inventory.Inventory;
import java.time.LocalDate;
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
