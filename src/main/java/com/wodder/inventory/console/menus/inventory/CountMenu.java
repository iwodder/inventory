package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.inventory.handlers.*;

import java.io.*;

public class CountMenu extends SubMenu {

	public CountMenu(CountHandler itemHandler) {
		this("Count Inventory", itemHandler);
	}

	private CountMenu(String name, CountHandler handler) {
		super(name, handler);
		handler.setMenu(this);
	}

	@Override
	public void printMenu(PrintStream out) {
		super.printMenu(out);
		out.println("Choose a location to count, enter on-hand values.");
		out.println("Enter \"exit\" to leave menu.");
	}
}
