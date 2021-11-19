package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;

import java.io.*;

public class UpdateItemMenu extends SubMenu {

	public UpdateItemMenu() {
		this("Update Item");
	}

	private UpdateItemMenu(String name) {
		super(name);
	}

	@Override
	public void printMenu(PrintStream out) {
		super.printMenu(out);
		out.println("Enter id of item to update with new value.");
		out.println("e.g. id=1 name=<new name>");
	}
}
