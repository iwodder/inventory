package com.wodder.inventory.console.menus.inventory.handlers;

import com.wodder.inventory.console.handlers.*;
import com.wodder.inventory.domain.*;
import com.wodder.inventory.models.*;

import java.io.*;
import java.util.*;

public class CountHandler extends InputHandler {
	private InventoryService service;
	private InventoryModel model;
	public CountHandler(InventoryService service) {
		this.service = service;
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		while (true) {
			String in = input.nextLine();
			if ("exit".equalsIgnoreCase(in)) {
				model = null;
				break;
			} else {
				if (model == null) {
					model = service.createNewInventory();
				}
				StringBuilder sb = new StringBuilder(in);
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
				out.printf("---- Counting %s ----%n", sb.toString());
				Iterator<InventoryCountModel> itr = model.itemsByLocation(in);
				for (int i = 1; itr.hasNext(); i++) {
					out.printf("%d) %s%n", i, itr.next().getName());
				}
			}
		}
	}
}