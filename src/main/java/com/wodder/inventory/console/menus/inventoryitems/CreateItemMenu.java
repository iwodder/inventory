package com.wodder.inventory.console.menus.inventoryitems;

import com.wodder.inventory.console.SubMenu;
import com.wodder.inventory.console.handlers.CreateItemHandler;
import com.wodder.inventory.console.models.InventoryItemModel;
import java.io.PrintStream;

public class CreateItemMenu extends SubMenu {
  private final InventoryItemModel model;

  public CreateItemMenu(CreateItemHandler itemHandler, InventoryItemModel model) {
    this("Create New Item", itemHandler, model);
  }

  private CreateItemMenu(String name, CreateItemHandler itemHandler, InventoryItemModel model) {
    super(name, itemHandler);
    this.model = model;
  }

  @Override
  public void printMenu(PrintStream out) {
    super.printMenu(out);
    out.println("Enter new item as key=value pairs, e.g. name=bread");
    out.println("Enter 'exit' to return to the previous menu");
  }
}
