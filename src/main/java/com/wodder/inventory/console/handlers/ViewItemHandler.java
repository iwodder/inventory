package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.models.*;
import com.wodder.inventory.dtos.*;

import java.io.*;
import java.util.*;

public class ViewItemHandler extends InputHandler {
	private final InventoryItemModel model;

	public ViewItemHandler(InventoryItemModel model) {
		this.model = model;
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		String in = input.nextLine();
		if ("all".equalsIgnoreCase(in)) {
			Result<List<InventoryItemDto>, String> result = model.getItems();
			if (result.isErr()) {
				err.println(result.getErr());
			} else {
				List<InventoryItemDto> items = result.getOk();
				ItemTableFormatter formatter = new ItemTableFormatter(items);
				out.print(formatter.formatToTable());
			}
		} else if ("exit".equalsIgnoreCase(in)) {
			menu.exitMenu();
		}
	}
}
