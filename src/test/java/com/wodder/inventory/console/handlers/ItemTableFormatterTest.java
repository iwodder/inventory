package com.wodder.inventory.console.handlers;

import com.wodder.inventory.dtos.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ItemTableFormatterTest {

	@Test
	@DisplayName("Handles data smaller than column title")
	void smaller_than_column_title() {
		List<InventoryItemDto> items = new ArrayList<>();
		items.add(InventoryItemDto.builder().withId(1L).withName("bread").withCategory("dry").build());
		ItemTableFormatter formatter = new ItemTableFormatter(items);
		assertEquals(loadExpectedOutput("smallerColumnValue"), formatter.formatToTable());
	}

	private String loadExpectedOutput(String fileName) {
		StringBuilder sb = new StringBuilder();
		try {
			URI fileUri = this.getClass().getResource(fileName).toURI();
			Files.readAllLines(Paths.get(fileUri))
					.forEach((line) -> {
						sb.append(line);
						sb.append(System.lineSeparator());
					});
		} catch (NullPointerException | URISyntaxException | IOException e) {
			fail(String.format("Unable to load expected output with name %s, cause %s", fileName,e.getMessage()));
		}
		return sb.toString();
	}
}
