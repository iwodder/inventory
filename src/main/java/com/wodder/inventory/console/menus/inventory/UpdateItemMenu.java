package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;

import java.io.*;

public class UpdateItemMenu extends SubMenu {

	public UpdateItemMenu(UpdateItemHandler handler) {
		this("Update Item", handler);
	}

	private UpdateItemMenu(String name, UpdateItemHandler handler) {
		super(name, handler);
	}

	@Override
	public void printMenu(PrintStream out) {
		super.printMenu(out);
		out.println("Enter id of item to update with new value.");
		out.println("e.g. id=1 name=<new name>");
	}
}
