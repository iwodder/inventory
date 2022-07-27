package com.wodder.console.handlers;

import com.wodder.console.MenuUtils;
import com.wodder.console.models.InventoryItemModel;
import com.wodder.inventory.dto.Result;
import com.wodder.product.dto.ProductDto;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

public class DeleteItemHandler extends InputHandler {
  private final InventoryItemModel model;

  public DeleteItemHandler(InventoryItemModel model) {
    this.model = model;
  }

  @Override
  public void handleInput(Scanner input, PrintStream out, PrintStream err) {
    String line = input.nextLine();
    if ("exit".equalsIgnoreCase(line)) {
      menu.exitMenu();
    } else {
      Map<String, String> keyValues = MenuUtils.extractKeyValuePairs(line);
      String id;
      if (keyValues.isEmpty()) {
        id = line;
      } else {
        id = keyValues.get("ID");
      }
      Result<Boolean, String> result = model.deleteItem(ProductDto.builder().withId(id).build());
      if (result.isOk()) {
        out.printf("Successfully deleted item with id of %s.%n", id);
      } else {
        err.printf("Unable to delete item with id of %s.%n", id);
      }
    }
  }
}
