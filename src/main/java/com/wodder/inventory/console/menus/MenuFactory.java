package com.wodder.inventory.console.menus;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.inventory.*;

public class MenuFactory {

	public MenuFactory() {

	}

	public RootMenu createMainMenu() {
		RootMenu main = new MainMenu();
		InventoryItemMenu inventoryItemMenu = new InventoryItemMenu();
		inventoryItemMenu.addMenu(new CreateItemMenu());
		main.addMenu(inventoryItemMenu);
		return main;
	}
}
