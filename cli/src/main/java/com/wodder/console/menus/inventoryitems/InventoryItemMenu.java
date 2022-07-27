package com.wodder.console.menus.inventoryitems;

import com.wodder.console.RootMenu;
import com.wodder.console.handlers.InputHandler;

public class InventoryItemMenu extends RootMenu {

  public InventoryItemMenu() {
    this("Inventory Item Menu", null);
  }

  public InventoryItemMenu(InputHandler handler) {
    this("Inventory Item Menu", handler);
    handler.setMenu(this);
  }

  private InventoryItemMenu(String name, InputHandler inputHandler) {
    super(name, inputHandler);
  }
}
