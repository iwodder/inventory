package com.wodder.console.handlers;

import com.wodder.console.MenuUtils;
import com.wodder.console.models.InventoryItemModel;
import com.wodder.inventory.dto.Result;
import com.wodder.product.dto.ProductDto;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

public class CreateItemHandler extends InputHandler {
  private final InventoryItemModel itemModel;

  public CreateItemHandler(InventoryItemModel itemModel) {
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
    ProductDto createdDto =
        ProductDto.builder()
            .withName(valuesMap.get("NAME"))
            .withCategory(valuesMap.get("CATEGORY"))
            .build();
    Result<ProductDto, String> result = itemModel.createItem(createdDto);
    if (result.isOk()) {
      ProductDto newItem = result.getOk();
      out.printf("Successfully created %s with id of %s%n", newItem.getName(), newItem.getId());
    } else {
      out.println(result.getErr());
    }
  }
}
