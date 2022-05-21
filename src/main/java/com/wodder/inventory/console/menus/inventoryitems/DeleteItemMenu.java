package com.wodder.inventory.console.menus.inventoryitems;

import com.wodder.inventory.console.SubMenu;
import com.wodder.inventory.console.handlers.DeleteItemHandler;
import java.io.PrintStream;

public class DeleteItemMenu extends SubMenu {

  public DeleteItemMenu(DeleteItemHandler itemHandler) {
    this("Delete Item", itemHandler);
  }

  private DeleteItemMenu(String name, DeleteItemHandler handler) {
    super(name, handler);
    handler.setMenu(this);
  }

  @Override
  public void printMenu(PrintStream out) {
    super.printMenu(out);
    out.println("Enter item id or name to delete, e.g. 1 or \"bread\"");
  }
}
