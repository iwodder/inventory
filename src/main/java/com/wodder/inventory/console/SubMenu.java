package com.wodder.inventory.console;

import com.wodder.inventory.console.handlers.*;

import java.io.*;

public class SubMenu extends ConsoleMenu {

	public SubMenu(String name) {
		this(name, null);
	}

	public SubMenu(String name, InputHandler handler) {
		super(name, handler);
		if (handler != null) {
			handler.setMenu(this);
		}
	}

	@Override
	public void printMenu(PrintStream out) {
		out.printf("====== %s ======%n", getMenuName());
	}
}
