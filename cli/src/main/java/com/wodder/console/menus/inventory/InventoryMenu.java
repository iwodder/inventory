package com.wodder.console.menus.inventory;

import com.wodder.console.RootMenu;
import com.wodder.console.handlers.InputHandler;

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
