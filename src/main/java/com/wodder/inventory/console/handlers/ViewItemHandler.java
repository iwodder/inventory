package com.wodder.inventory.console.handlers;

import com.wodder.inventory.models.*;

import java.io.*;
import java.util.*;

public class ViewItemHandler extends InputHandler {
	private final com.wodder.inventory.console.models.InventoryItemModel model;

	public ViewItemHandler(com.wodder.inventory.console.models.InventoryItemModel model) {
		this.model = model;
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		String in = input.nextLine();
		if ("all".equalsIgnoreCase(in)) {
			Result<List<InventoryItemModel>, String> result = model.getItems();
			if (result.isErr()) {
				err.println(result.getErr());
			} else {
				List<InventoryItemModel> items = result.getOk();
				ItemTableFormatter formatter = new ItemTableFormatter(items);
				out.print(formatter.formatToTable());
			}
		} else if ("exit".equalsIgnoreCase(in)) {
			menu.exitMenu();
		}
	}
}
