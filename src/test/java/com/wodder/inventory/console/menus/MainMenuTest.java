package com.wodder.inventory.console.menus;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.inventory.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest extends BaseMenuTest {

	@Test
	@DisplayName("Main menu prompts user for input")
	void test1() {
		setChars("1\n");
		ConsoleMenu main = new MainMenu();
		main.handleInput(in, out, err);
		assertEquals(String.format("Please choose a menu%n> "), baosOut.toString());
	}

	@Test
	@DisplayName("Main menu gracefully handles non-number input")
	void test2() {
		setChars("a\n");
		ConsoleMenu main = new MainMenu();
		main.handleInput(in, out, err);
		assertEquals(String.format("Unrecognized input, please enter a number.%n"), baosErr.toString());
	}

	@Test
	@DisplayName("Main menu gracefully handles non-number input")
	void test3() {
		setChars("2\n");
		ConsoleMenu main = new MainMenu();
		main.handleInput(in, out, err);
		assertEquals(String.format("Unrecognized input, please enter a number.%n"), baosErr.toString());
	}
}
