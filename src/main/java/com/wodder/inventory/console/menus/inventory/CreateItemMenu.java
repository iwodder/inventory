package com.wodder.inventory.console.menus.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.dtos.*;

import java.io.*;
import java.util.*;

public class CreateItemMenu extends SubMenu implements InputHandler {
	InventoryItemDto createdDto;

	CreateItemMenu() {
		super("Create New Item");
		setInputHandler(this);
	}

	@Override
	public void printMenu(PrintStream out) {

	}

	@Override
	public void handleInput(Scanner input, PrintStream out) {
		String in = input.nextLine();
		PrimitiveIterator.OfInt chars = in.chars().iterator();
		Map<String, String> valuesMap = new HashMap<>();
		StringBuffer buff = new StringBuffer();
		String key = null;
		String value = null;
		int last_space = -1;
		int first_space = -1;
		while (chars.hasNext()) {
			char c = (char) chars.next().intValue();
			if (c != '=') {
				buff.append(c);
			}
			if (c == '=' && key == null) {
				key = buff.toString();
				buff.delete(0, buff.length());
				while (chars.hasNext()) {
					c = (char) chars.next().intValue();
					if (c != '=') {
						buff.append(c);
					} else {
						break;
					}
				}
			}
			last_space = buff.lastIndexOf(" ");
			first_space = buff.indexOf(" ");
			if (last_space > -1 && last_space > first_space) {
				value = buff.substring(0, last_space);
			} else if (!chars.hasNext()) {
				value = buff.toString();
			}
			if (key != null && value != null) {
				valuesMap.put(key.toUpperCase(), value);
				if (buff.length() > 0) {
					key = buff.substring(last_space + 1, buff.length());
					buff.delete(0, buff.length());
				}
				value = null;
			}
		}
		createdDto = InventoryItemDto.builder()
				.withName(valuesMap.get("NAME"))
				.withCategory(valuesMap.get("CATEGORY"))
				.build();
	}
}
