package com.wodder.inventory.console.handlers;

import com.wodder.inventory.models.*;

import java.util.*;

public class ItemTableFormatter {
	private final List<InventoryItemModel> items;

	public ItemTableFormatter(List<InventoryItemModel> items) {
		this.items = new ArrayList<>(items);
	}

	public String formatToTable() {
		StringBuilder output = new StringBuilder();
		items.sort(Comparator.comparing(InventoryItemModel::getId));
		int maxId = Math.max(items.stream()
				.map(InventoryItemModel::getId)
				.mapToInt(Long::intValue)
				.max().orElseGet(() -> 20), "ID".length());
		int maxName = Math.max(items.stream()
				.map(InventoryItemModel::getName)
				.max(Comparator.comparing(String::length))
				.map(String::length)
				.orElseGet(() -> 20), "NAME".length());
		int maxCategory = Math.max(items.stream()
				.map(InventoryItemModel::getCategory)
				.max(Comparator.comparing(String::length))
				.map(String::length)
				.orElseGet(() -> 20), "CATEGORY".length());

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
		for (InventoryItemModel dto : items) {
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
