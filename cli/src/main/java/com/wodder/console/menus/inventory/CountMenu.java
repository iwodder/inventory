package com.wodder.console.menus.inventory;

import com.wodder.console.SubMenu;
import com.wodder.console.menus.inventory.handlers.CountHandler;
import java.io.PrintStream;

public class CountMenu extends SubMenu {

  public CountMenu(CountHandler itemHandler) {
    this("InventoryCount Inventory", itemHandler);
  }

  private CountMenu(String name, CountHandler handler) {
    super(name, handler);
    handler.setMenu(this);
  }

  @Override
  public void printMenu(PrintStream out) {
    super.printMenu(out);
    out.println("Choose a location to count, enter on-hand values.");
    out.println("Enter \"exit\" to leave menu.");
  }
}
