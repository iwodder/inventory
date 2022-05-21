package com.wodder.inventory.console.menus.inventory.handlers;

import com.wodder.inventory.console.handlers.InputHandler;
import com.wodder.inventory.dto.InventoryDto;
import java.io.PrintStream;
import java.util.Scanner;

// TODO Revisit this once the Inventory Service is done
public class CountHandler extends InputHandler {
  private InventoryDto model;

  @Override
  public void handleInput(Scanner input, PrintStream out, PrintStream err) {
    while (true) {
      String in = input.nextLine();
      if ("exit".equalsIgnoreCase(in)) {
        model = null;
        break;
      } else {
        if (model == null) {
          //model = service.createNewInventory();
        }
        StringBuilder sb = new StringBuilder(in);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        out.printf("---- Counting %s ----%n", sb);
        /*Iterator<InventoryCountModel> itr = model.itemsByLocation(in);
        for (int i = 1; itr.hasNext(); i++) {
          out.printf("%d) %s%n", i, itr.next().getProductId());
        }*/
      }
    }
  }
}
