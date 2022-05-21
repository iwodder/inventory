package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.RootMenu;
import com.wodder.inventory.console.handlers.InputHandler;

public class InventoryMenu extends RootMenu {

  public InventoryMenu() {
    this("Inventory Menu", null);
  }

  public InventoryMenu(InputHandler handler) {
    this("Inventory Menu", handler);
    handler.setMenu(this);
  }

  private InventoryMenu(String title, InputHandler handler) {
    super(title, handler);
  }
}
