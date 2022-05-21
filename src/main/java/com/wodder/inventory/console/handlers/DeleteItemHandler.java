package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.MenuUtils;
import com.wodder.inventory.dto.ProductModel;
import com.wodder.inventory.dto.Result;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

public class DeleteItemHandler extends InputHandler {
  private final com.wodder.inventory.console.models.InventoryItemModel model;

  public DeleteItemHandler(com.wodder.inventory.console.models.InventoryItemModel model) {
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
      Result<Boolean, String> result = model.deleteItem(ProductModel.builder().withId(id).build());
      if (result.isOk()) {
        out.printf("Successfully deleted item with id of %s.%n", id);
      } else {
        err.printf("Unable to delete item with id of %s.%n", id);
      }
    }
  }
}
