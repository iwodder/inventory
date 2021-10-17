package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.models.*;
import com.wodder.inventory.dtos.*;

import java.io.*;
import java.util.*;

public class CreateItemMenu extends SubMenu implements InputHandler {
	private InventoryItemModel model;

	public CreateItemMenu() {
		super("Create New Item");
		setInputHandler(this);
	}

	public CreateItemMenu(InventoryItemModel model) {
		this();
		this.model = model;
	}

	@Override
	public void printMenu(PrintStream out) {
		out.printf("====== %s ======%n", getMenuName());
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		String in = input.nextLine();
		Map<String, String> valuesMap = MenuUtils.extractKeyValuePairs(in);
		InventoryItemDto createdDto = InventoryItemDto.builder()
				.withName(valuesMap.get("NAME"))
				.withCategory(valuesMap.get("CATEGORY"))
				.build();
		Result<InventoryItemDto, String> result = model.createItem(createdDto);
		if (result.isOK()) {
			InventoryItemDto newItem = result.getOk();
			out.printf("Successfully created %s with id of %d%n", newItem.getName(), newItem.getId());
		} else {
			out.println(result.getErr());
		}
	}
}
