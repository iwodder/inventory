package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.models.*;
import com.wodder.inventory.dtos.*;

import java.io.*;
import java.util.*;

public class UpdateItemHandler extends InputHandler {
	private final InventoryItemModel model;

	public UpdateItemHandler(InventoryItemModel model) {
		this.model = model;
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		String line = input.nextLine();
		Map<String, String> values = MenuUtils.extractKeyValuePairs(line);
		InventoryItemDto dto = InventoryItemDto.builder()
				.withId(Long.parseLong(values.get("ID")))
				.withName(values.get("NAME"))
				.build();
		Result<InventoryItemDto, String> result = model.updateItem(dto);
		if (result.isOK()) {
			out.println("Item updated successfully");
		} else {
			err.println("Unable to update item");
		}
	}
}
