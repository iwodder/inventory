package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;

import java.io.*;
import java.util.*;

public class InventoryItemMenu extends RootMenu implements InputHandler {

	public InventoryItemMenu() {
		super("Inventory Item Menu");
		setInputHandler(this);
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		Integer selection = Integer.parseInt(input.next());
		setActiveMenu(selection);
	}
}
