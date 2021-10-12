package com.wodder.inventory.console;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MenuUtilsTest {

	@Test
	@DisplayName("Extracts single key value pair with no spaces")
	void extractKeyValuePairs() {
		Map<String, String> values = MenuUtils.extractKeyValuePairs("name=Soy_Milk");
		assertEquals("Soy_Milk", values.get("NAME"));
	}

	@Test
	@DisplayName("Extracts single key value pair with spaces")
	void extractKeyValuePairs1() {
		Map<String, String> values = MenuUtils.extractKeyValuePairs("name=\"Soy Milk\"");
		assertEquals("Soy Milk", values.get("NAME"));
	}

	@Test
	@DisplayName("Extracts two key value pairs with no spaces")
	void extractKeyValuePairs2() {
		Map<String, String> values = MenuUtils.extractKeyValuePairs("name=Soy_Milk price=2.99");
		assertEquals("Soy_Milk", values.get("NAME"));
		assertEquals("2.99", values.get("PRICE"));
	}

	@Test
	@DisplayName("Extracts two key value pairs with spaces")
	void extractKeyValuePairs3() {
		Map<String, String> values = MenuUtils.extractKeyValuePairs("name=\"Soy Milk\" price=2.99");
		assertEquals("Soy Milk", values.get("NAME"));
		assertEquals("2.99", values.get("PRICE"));
	}

	@Test
	@DisplayName("Extracts many key value pairs with spaces")
	void extractKeyValuePairs4() {
		Map<String, String> values = MenuUtils.extractKeyValuePairs("name=\"Soy Milk\" price=2.99 key3=idgaf");
		assertEquals("Soy Milk", values.get("NAME"));
		assertEquals("2.99", values.get("PRICE"));
		assertEquals("idgaf", values.get("KEY3"));
	}
}
