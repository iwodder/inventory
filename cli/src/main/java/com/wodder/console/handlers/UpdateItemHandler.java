package com.wodder.console.handlers;

import com.wodder.console.MenuUtils;
import com.wodder.console.models.InventoryItemModel;
import com.wodder.inventory.dto.Result;
import com.wodder.product.dto.ProductDto;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

public class UpdateItemHandler extends InputHandler {
  private final InventoryItemModel model;

  public UpdateItemHandler(InventoryItemModel model) {
    this.model = model;
  }

  @Override
  public void handleInput(Scanner input, PrintStream out, PrintStream err) {
    String line = input.nextLine();
    if ("exit".equalsIgnoreCase(line)) {
      menu.exitMenu();
    } else {
      processInput(out, err, line);
    }
  }

  private void processInput(PrintStream out, PrintStream err, String line) {
    Map<String, String> values = MenuUtils.extractKeyValuePairs(line);
    ProductDto dto = ProductDto.fromMap(values);
    Result<ProductDto, String> result = model.updateItem(dto);
    if (result.isOk()) {
      out.println("Item updated successfully");
    } else {
      err.println("Unable to update item");
      err.println(result.getErr());
    }
  }
}
