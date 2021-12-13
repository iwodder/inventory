package com.wodder.inventory.console.menus;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;
import com.wodder.inventory.console.menus.inventoryitems.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest extends BaseMenuTest {
	private int loopCnt;
	private InputHandler handler;
	private ConsoleMenu main;

	@BeforeEach
	protected void setup() {
		super.setup();
		loopCnt = 0;
		handler = new DefaultRootMenuHandler();
		main = new MainMenu(handler);
	}

	@Test
	@DisplayName("Main menu prompts user for input")
	void test1() {
		setChars("1\n");
		main.process(in, out, err);
		assertEquals(String.format("Please choose a menu%n> "), baosOut.toString());
	}

	@Test
	@DisplayName("Main menu gracefully handles non-number input")
	void test2() {
		setChars("a\n");
		main.process(in, out, err);
		assertEquals(String.format("Unrecognized input, please enter a number.%n"), baosErr.toString());
	}

	@Test
	@DisplayName("Main menu gracefully handles unknown menu choice")
	void test3() {
		setChars("2\n");
		main.process(in, out, err);
		assertEquals(String.format("Unknown menu for choice 2%nPlease choose a valid menu%n"), baosErr.toString());
	}

	@Test
	@Disabled
	@DisplayName("Main menu loops until valid input is received")
	void test4() {
		setChars("a\nb\n1\n");
		main.process(in, out, err);
		assertEquals(3, loopCnt);
	}

	class TestMainMenu extends MainMenu {

		TestMainMenu() {
			super(null);
		}

		@Override
		public void process(Scanner input, PrintStream out, PrintStream err) {
			loopCnt += 1;
			super.process(input, out, err);
		}
	}
}
