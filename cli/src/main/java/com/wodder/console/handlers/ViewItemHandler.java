package com.wodder.console.handlers;

import com.wodder.console.models.InventoryItemModel;
import com.wodder.inventory.dto.Result;
import com.wodder.product.dto.ProductDto;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class ViewItemHandler extends InputHandler {
  private final InventoryItemModel model;

  public ViewItemHandler(InventoryItemModel model) {
    this.model = model;
  }

  @Override
  public void handleInput(Scanner input, PrintStream out, PrintStream err) {
    String in = input.nextLine();
    if ("all".equalsIgnoreCase(in)) {
      Result<List<ProductDto>, String> result = model.getItems();
      if (result.isErr()) {
        err.println(result.getErr());
      } else {
        List<ProductDto> items = result.getOk();
        ItemTableFormatter formatter = new ItemTableFormatter(items);
        out.print(formatter.formatToTable());
      }
    } else if ("exit".equalsIgnoreCase(in)) {
      menu.exitMenu();
    }
  }
}
