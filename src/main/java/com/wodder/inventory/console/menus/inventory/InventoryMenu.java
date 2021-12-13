package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;

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
