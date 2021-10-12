package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.dtos.*;

import java.io.*;
import java.util.*;

public class CreateItemMenu extends SubMenu implements InputHandler {
	InventoryItemDto createdDto;

	public CreateItemMenu() {
		super("Create New Item");
		setInputHandler(this);
	}

	@Override
	public void printMenu(PrintStream out) {
		out.printf("====== %s ======%n", getMenuName());
	}

	@Override
	public void handleInput(Scanner input, PrintStream out) {
		String in = input.nextLine();
		Map<String, String> valuesMap = MenuUtils.extractKeyValuePairs(in);
		createdDto = InventoryItemDto.builder()
				.withName(valuesMap.get("NAME"))
				.withCategory(valuesMap.get("CATEGORY"))
				.build();
	}
}
