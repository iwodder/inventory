package com.wodder.inventory.console.handlers;

import com.wodder.inventory.dto.ProductModel;
import com.wodder.inventory.dto.Result;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class ViewItemHandler extends InputHandler {
  private final com.wodder.inventory.console.models.InventoryItemModel model;

  public ViewItemHandler(com.wodder.inventory.console.models.InventoryItemModel model) {
    this.model = model;
  }

  @Override
  public void handleInput(Scanner input, PrintStream out, PrintStream err) {
    String in = input.nextLine();
    if ("all".equalsIgnoreCase(in)) {
      Result<List<ProductModel>, String> result = model.getItems();
      if (result.isErr()) {
        err.println(result.getErr());
      } else {
        List<ProductModel> items = result.getOk();
        ItemTableFormatter formatter = new ItemTableFormatter(items);
        out.print(formatter.formatToTable());
      }
    } else if ("exit".equalsIgnoreCase(in)) {
      menu.exitMenu();
    }
  }
}
