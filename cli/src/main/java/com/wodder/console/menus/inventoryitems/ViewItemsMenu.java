package com.wodder.console.menus.inventoryitems;

import com.wodder.console.SubMenu;
import com.wodder.console.handlers.ViewItemHandler;
import java.io.PrintStream;

public class ViewItemsMenu extends SubMenu {

  public ViewItemsMenu(ViewItemHandler handler) {
    this("View Inventory Item(s)", handler);
  }

  private ViewItemsMenu(String name, ViewItemHandler handler) {
    super(name, handler);
  }

  @Override
  public void printMenu(PrintStream out) {
    super.printMenu(out);
    out.println("Enter id of item to view or \"all\" to see all items.");
  }
}
