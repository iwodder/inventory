package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;

import java.io.*;
import java.util.*;

public class InventoryItemMenu extends RootMenu implements InputHandler {

	InventoryItemMenu() {
		super("Inventory Item Menu");
	}

	@Override
	public void handleInput(Scanner input, PrintStream out) {
		Integer selection = Integer.parseInt(input.next());
		setActiveMenu(selection);
	}
}
