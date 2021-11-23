package com.wodder.inventory.console.handlers;

import com.wodder.inventory.dtos.*;

import java.util.*;

public class ItemTableFormatter {
	private final List<InventoryItemDto> items;

	public ItemTableFormatter(List<InventoryItemDto> items) {
		this.items = new ArrayList<>(items);
	}

	public String formatToTable() {
		StringBuilder output = new StringBuilder();
		items.sort(Comparator.comparing(InventoryItemDto::getId));
		int maxId = items.stream()
				.map(InventoryItemDto::getId)
				.mapToInt(Long::intValue)
				.max().orElseGet(() -> 20);
		int maxName = items.stream()
				.map(InventoryItemDto::getName)
				.max(Comparator.comparing(String::length))
				.map(String::length)
				.orElseGet(() -> 20);
		int maxCategory = items.stream()
				.map(InventoryItemDto::getCategory)
				.max(Comparator.comparing(String::length))
				.map(String::length)
				.orElseGet(() -> 20);

		int lineLength = maxId + 4 + maxName + 4 + maxCategory + 4 + 4;
		String rowSep = "-".repeat(lineLength);
		output.append(rowSep);
		output.append(System.lineSeparator());
		output.append("|");
		output.append(centerText("ID", maxId));
		output.append("|");
		output.append(centerText("NAME", maxName));
		output.append("|");
		output.append(centerText("CATEGORY", maxCategory));
		output.append("|");
		output.append(System.lineSeparator());
		output.append(rowSep);
		output.append(System.lineSeparator());
		for (InventoryItemDto dto : items) {
			output.append(String.format(
					"|%s|%s|%s|", centerText(dto.getId().toString(), maxId),
					centerText(dto.getName(), maxName), centerText(dto.getCategory(), maxCategory)));
			output.append(System.lineSeparator());
			output.append(rowSep);
			output.append(System.lineSeparator());
		}
		return output.toString();
	}

	private String centerText(String value, int maxWidth) {
		StringBuilder sb = new StringBuilder(maxWidth);
		int padding = maxWidth + 4 - value.length();
		int lPad = padding / 2;
		int rPad = padding - lPad;
		sb.append(" ".repeat(rPad));
		sb.append(value);
		sb.append(" ".repeat(lPad));
		return sb.toString();
	}
}
