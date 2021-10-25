package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;
import com.wodder.inventory.console.models.*;

import java.io.*;

public class CreateItemMenu extends SubMenu {
	private final InventoryItemModel model;

	public CreateItemMenu(CreateItemHandler itemHandler, InventoryItemModel model) {
		this("Create New Item", itemHandler, model);
	}

	private CreateItemMenu(String name, CreateItemHandler itemHandler, InventoryItemModel model) {
		super(name, itemHandler);
		itemHandler.setMenu(this);
		this.model = model;
	}

	@Override
	public void printMenu(PrintStream out) {
		super.printMenu(out);
		out.println("Enter new item as key=value pairs, e.g. name=bread");
	}
}
