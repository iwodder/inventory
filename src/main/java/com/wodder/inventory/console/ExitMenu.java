package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

public class ExitMenu extends SubMenu implements InputHandler {

	public ExitMenu() {
		super("Exit");
		setInputHandler(this);
	}

	@Override
	public void printMenu(PrintStream out) {
		out.print("Exiting...");
	}

	@Override
	public void handleInput(Scanner in, PrintStream out, PrintStream err) {
		getParentMenu().exitMenu();
	}
}
