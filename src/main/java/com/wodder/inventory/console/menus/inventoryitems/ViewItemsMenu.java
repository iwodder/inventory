package com.wodder.inventory.console.menus.inventoryitems;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;

import java.io.*;

public class ViewItemsMenu extends SubMenu {

	public ViewItemsMenu(ViewItemHandler handler) {
		this("View Inventory Item(s)", handler);
	}

	private ViewItemsMenu(String name, ViewItemHandler handler) {
		super(name, handler);
	}

	@Override
	public void printMenu(PrintStream out) {
		super.printMenu(out);
		out.println("Enter id of item to view or \"all\" to see all items.");
	}
}
