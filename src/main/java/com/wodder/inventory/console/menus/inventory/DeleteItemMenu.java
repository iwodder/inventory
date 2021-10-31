package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;

import java.io.*;

public class DeleteItemMenu extends SubMenu {

	public DeleteItemMenu() {
		super("Delete Item");
	}

	@Override
	public void printMenu(PrintStream out) {
		super.printMenu(out);
		out.println("Enter item id or name to delete, e.g. 1 or \"bread\"");
	}
}
