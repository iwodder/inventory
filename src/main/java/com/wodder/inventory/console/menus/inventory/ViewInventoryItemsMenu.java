package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;

import java.io.*;

public class ViewInventoryItemsMenu extends SubMenu {

	public ViewInventoryItemsMenu() {
		this("View Inventory Item(s)");
	}

	private ViewInventoryItemsMenu(String name) {
		super(name);
	}

	@Override
	public void printMenu(PrintStream out) {
		super.printMenu(out);
		out.println("Enter id of item to view or \"all\" to see all items.");
	}
}
