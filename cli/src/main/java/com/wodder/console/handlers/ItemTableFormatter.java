package com.wodder.console.handlers;

import com.wodder.product.dto.ProductDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItemTableFormatter {
  private final List<ProductDto> items;

  public ItemTableFormatter(List<ProductDto> items) {
    this.items = new ArrayList<>(items);
  }

  public String formatToTable() {
    StringBuilder output = new StringBuilder();
    items.sort(Comparator.comparing(ProductDto::getId));
    int maxId =
        Math.max(
            items.stream()
                .map(ProductDto::getId)
                .mapToInt(String::length)
                .max()
                .orElseGet(() -> 20),
            "ID".length());
    int maxName =
        Math.max(
            items.stream()
                .map(ProductDto::getName)
                .max(Comparator.comparing(String::length))
                .map(String::length)
                .orElseGet(() -> 20),
            "NAME".length());
    int maxCategory =
        Math.max(
            items.stream()
                .map(ProductDto::getCategory)
                .max(Comparator.comparing(String::length))
                .map(String::length)
                .orElseGet(() -> 20),
            "CATEGORY".length());

    int lineLength = maxId + 4 + maxName + 4 + maxCategory + 4 + 4;
    String rowSep = "-".repeat(lineLength);
    output.append(rowSep);
    output.append(System.lineSeparator());
    output.append("|");
    output.append(centerText("ID", maxId));
    output.append("|");
    output.append(centerText("NAME", maxName));
    output.append("|");
    output.append(centerText("CATEGORY", maxCategory));
    output.append("|");
    output.append(System.lineSeparator());
    output.append(rowSep);
    output.append(System.lineSeparator());
    for (ProductDto dto : items) {
      output.append(
          String.format(
              "|%s|%s|%s|",
              centerText(dto.getId().toString(), maxId),
              centerText(dto.getName(), maxName),
              centerText(dto.getCategory(), maxCategory)));
      output.append(System.lineSeparator());
      output.append(rowSep);
      output.append(System.lineSeparator());
    }
    return output.toString();
  }

  private String centerText(String value, int maxWidth) {
    StringBuilder sb = new StringBuilder(maxWidth);
    int padding = maxWidth + 4 - value.length();
    int leftPad = padding / 2;
    int rightPad = padding - leftPad;
    sb.append(" ".repeat(rightPad));
    sb.append(value);
    sb.append(" ".repeat(leftPad));
    return sb.toString();
  }
}
