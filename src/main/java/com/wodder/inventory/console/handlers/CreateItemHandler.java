package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.MenuUtils;
import com.wodder.inventory.dto.ProductModel;
import com.wodder.inventory.dto.Result;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

public class CreateItemHandler extends InputHandler {
  private final com.wodder.inventory.console.models.InventoryItemModel itemModel;

  public CreateItemHandler(com.wodder.inventory.console.models.InventoryItemModel itemModel) {
    this.itemModel = itemModel;
  }

  @Override
  public void handleInput(Scanner input, PrintStream out, PrintStream err) {
    String in = input.nextLine();
    if ("exit".equalsIgnoreCase(in)) {
      menu.exitMenu();
    } else {
      processNewItem(in, out);
    }
  }

  private void processNewItem(String in, PrintStream out) {
    Map<String, String> valuesMap = MenuUtils.extractKeyValuePairs(in);
    ProductModel createdDto =
        ProductModel.builder()
            .withName(valuesMap.get("NAME"))
            .withCategory(valuesMap.get("CATEGORY"))
            .build();
    Result<ProductModel, String> result = itemModel.createItem(createdDto);
    if (result.isOk()) {
      ProductModel newItem = result.getOk();
      out.printf("Successfully created %s with id of %s%n", newItem.getName(), newItem.getId());
    } else {
      out.println(result.getErr());
    }
  }
}
