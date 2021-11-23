package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.models.*;
import com.wodder.inventory.dtos.*;

import java.io.*;
import java.util.*;

public class DeleteItemHandler extends InputHandler {
	private final InventoryItemModel model;

	public DeleteItemHandler(InventoryItemModel model) {
		this.model = model;
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		String line = input.nextLine();
		if ("exit".equalsIgnoreCase(line)) {
			menu.exitMenu();
		} else {
			Map<String, String> keyValues = MenuUtils.extractKeyValuePairs(line);
			long id;
			if (keyValues.isEmpty()) {
				id = Long.parseLong(line);
			} else {
				id = Long.parseLong(keyValues.get("ID"));
			}
			Result<Boolean, String> result = model.deleteItem(InventoryItemDto.builder().withId(id).build());
			if (result.isOK()) {
				out.printf("Successfully deleted item with id of %d.%n", id);
			} else {
				err.printf("Unable to delete item with id of %d.%n", id);
			}
		}
	}
}