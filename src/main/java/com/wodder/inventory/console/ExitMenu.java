package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

public class ExitMenu extends SubMenu implements InputHandler {

	public ExitMenu() {
		super("Exit");
	}

	@Override
	public void printMenu(PrintStream out) {
		out.println("Exiting...");
	}

	@Override
	public void handleInput(Scanner in, PrintStream out, PrintStream err) {
		exitMenu();
	}
}
