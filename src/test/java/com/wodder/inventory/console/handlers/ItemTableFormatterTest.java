package com.wodder.inventory.console.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.wodder.inventory.dto.ProductModel;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemTableFormatterTest {

  @Test
  @DisplayName("Handles data smaller than column title")
  void smaller_than_column_title() {
    List<ProductModel> items = new ArrayList<>();
    items.add(ProductModel.builder().withId("1").withName("bread").withCategory("dry").build());
    ItemTableFormatter formatter = new ItemTableFormatter(items);
    assertEquals(loadExpectedOutput("smallerColumnValue"), formatter.formatToTable());
  }

  private String loadExpectedOutput(String fileName) {
    StringBuilder sb = new StringBuilder();
    try {
      URI fileUri = this.getClass().getResource(fileName).toURI();
      Files.readAllLines(Paths.get(fileUri))
          .forEach(
              (line) -> {
                sb.append(line);
                sb.append(System.lineSeparator());
              });
    } catch (NullPointerException | URISyntaxException | IOException e) {
      fail(
          String.format(
              "Unable to load expected output with name %s, cause %s", fileName, e.getMessage()));
    }
    return sb.toString();
  }
}
