package com.wodder.inventory.console.menus;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import com.wodder.inventory.console.models.*;
import com.wodder.inventory.domain.*;

public class MenuFactory {
	private final ServiceFactory serviceFactory;

	public MenuFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	public ConsoleMenu createMainMenu() {
		RootMenu main = new MainMenu(new DefaultRootMenuHandler());
		InventoryItemMenu inventoryItemMenu = createInventoryMenu();
		main.addMenu(inventoryItemMenu);
		main.addMenu(new ExitMenu(new ExitHandler()));
		return main;
	}

	private InventoryItemMenu createInventoryMenu() {
		InventoryItemMenu inventoryItemMenu = new InventoryItemMenu(new DefaultRootMenuHandler());
		InventoryItemModel model = new InventoryItemModelImpl(serviceFactory.getService(ItemStorage.class));
		inventoryItemMenu.addMenu(new CreateItemMenu(new CreateItemHandler(model), model));
		inventoryItemMenu.addMenu(new DeleteItemMenu(new DeleteItemHandler(model)));
		inventoryItemMenu.addMenu(new UpdateItemMenu(new UpdateItemHandler(model)));
		inventoryItemMenu.addMenu(new ViewItemsMenu(new ViewItemHandler(model)));
		inventoryItemMenu.addMenu(new ExitMenu(new ExitHandler()));
		return inventoryItemMenu;
	}

}
