package com.wodder.inventory.console;

import com.wodder.inventory.console.handlers.*;

import java.io.*;

public class ExitMenu extends SubMenu {

	public ExitMenu(InputHandler handler) {
		super("Exit", handler);
	}

	public ExitMenu() {
		this(null);
	}

	@Override
	public void printMenu(PrintStream out) {
		out.print("Exiting...");
	}
}
