package com.wodder.inventory.console.menus;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;
import com.wodder.inventory.console.menus.inventory.*;
import com.wodder.inventory.console.models.*;
import com.wodder.inventory.domain.*;

public class MenuFactory {

	public MenuFactory() {

	}

	public RootMenu createMainMenu() {
		RootMenu main = new MainMenu(new DefaultRootMenuHandler());
		InventoryItemMenu inventoryItemMenu = new InventoryItemMenu();
		InventoryItemModel model = new InventoryItemModelImpl(new ServiceFactoryImpl());
		inventoryItemMenu.addMenu(new CreateItemMenu(new CreateItemHandler(model), model));
		main.addMenu(inventoryItemMenu);
		main.addMenu(new ExitMenu());
		return main;
	}
}
