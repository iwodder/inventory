package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

public class InventoryItemMenu extends ConsoleMenu {

	String menuText = "Inventory Item Menu\n1) Create new item\n2) Update existing item\n3) Delete item\n4) List all items";

	InventoryItemMenu(String name) {
		super(name);
	}

	InventoryItemMenu(PrintStream out, Scanner in) {
		super(null);
	}

	@Override
	public void printMenu(PrintStream out) {
		out.print(menuText);
	}

	@Override
	public void readInput(Scanner input) {
		/*boolean exit = false;
		while (!exit) {
			try {
				int choice = Integer.parseInt(input.next());
				switch (choice) {
					case 1:
						outputStream.print("Creating new item");
						break;
					case 2:
						outputStream.print("Updating existing item");
						break;
					case 3:
						outputStream.print("Deleting existing item");
						break;
					case 4:
						outputStream.print("Listing all items");
						break;
					case 5:
						outputStream.print("Exiting");
						exit = true;
						break;
					default:
						outputStream.print("Unrecognized choice");
				}
			} catch (NumberFormatException numberFormatException) {
				outputStream.println("Please enter a number");
			}
		}*/
	}

}
