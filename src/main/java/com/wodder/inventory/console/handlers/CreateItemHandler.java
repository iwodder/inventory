package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.models.*;
import com.wodder.inventory.dtos.*;

import java.io.*;
import java.util.*;

public class CreateItemHandler extends InputHandler {
	private final InventoryItemModel itemModel;

	public CreateItemHandler(InventoryItemModel itemModel) {
		this.itemModel = itemModel;
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		String in = input.nextLine();
		if ("exit".equalsIgnoreCase(in)) {
			menu.exitMenu();
		} else {
			processNewItem(in, out);
		}
	}

	private void processNewItem(String in, PrintStream out) {
		Map<String, String> valuesMap = MenuUtils.extractKeyValuePairs(in);
		InventoryItemDto createdDto = InventoryItemDto.builder()
				.withName(valuesMap.get("NAME"))
				.withCategory(valuesMap.get("CATEGORY"))
				.build();
		Result<InventoryItemDto, String> result = itemModel.createItem(createdDto);
		if (result.isOK()) {
			InventoryItemDto newItem = result.getOk();
			out.printf("Successfully created %s with id of %d%n", newItem.getName(), newItem.getId());
		} else {
			out.println(result.getErr());
		}
	}
}