package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;

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
