package com.wodder.inventory.console;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RootMenuTest implements InputHandler {
	private ByteArrayOutputStream baos;
	private ConsoleMenu menu;
	private boolean inputHandled = false;

	@BeforeEach
	void setup() {
		baos = new ByteArrayOutputStream();
		menu = new RootMenu("RootMenu");
	}

	@Test
	@DisplayName("RootMenu accepts all other menus")
	void add_menu() {
		menu.addMenu(new SubMenu("Sub menu"));
		assertEquals(1, menu.subMenuCnt());
	}

	@Test
	@DisplayName("Adding submenu sets parent menu")
	void add_menu_1() {
		ConsoleMenu invMenu = new SubMenu("Inventory Item Menu");
		menu.addMenu(invMenu);
		assertSame(menu, invMenu.getParentMenu());
	}

	@Test
	@DisplayName("Empty RootMenu prints name and exit command as 1")
	void print_menu() {
		menu.printMenu(new PrintStream(baos));
		String output = baos.toString();
		String expected = String.format("====== RootMenu ======%n1) Exit Menu%n");
		assertEquals(expected, output);
	}

	@Test
	@DisplayName("RootMenu prints name, SubMenu title, and exit command")
	void print_menu_1() {
		menu.addMenu(new SubMenu("Inventory Item Menu"));
		menu.printMenu(new PrintStream(baos));
		String output = baos.toString();
		String expected = String.format("====== RootMenu ======%n1) Inventory Item Menu%n2) Exit Menu%n");
		assertEquals(expected, output);
	}

	@Test
	@DisplayName("When RootMenu isn't active SubMenu prints")
	void print_menu_2() {
		ConsoleMenu testMenu = new SubMenu("Test Menu") {
			@Override
			public void printMenu(PrintStream out) {
				out.print("====== Test Menu ======");
			}
		};
		menu.addMenu(testMenu);
		menu.setActiveMenu(1);
		menu.printMenu(new PrintStream(baos));
		String output = baos.toString();
		String expected = "====== Test Menu ======";
		assertEquals(expected, output);
	}

	@Test
	@DisplayName("Exiting submenu returns to root menu")
	void exit_submenu() {
		ConsoleMenu menu = new RootMenu("Root Menu");
		ConsoleMenu subMenu = new SubMenu("Inv. Item Menu") {
			ConsoleMenu parent;
			@Override
			public void printMenu(PrintStream out) {
				out.print("====== Test Menu ======");
			}
		};

		menu.addMenu(subMenu);

		menu.setActiveMenu(1);
		assertSame(subMenu, menu.getActiveMenu());
		subMenu.exitMenu();
		assertSame(menu, menu.getActiveMenu());
	}

	@Test
	@DisplayName("Can add RootMenu to RootMenu")
	void root_menu_test() {
		ConsoleMenu menu1 = new RootMenu("Root Menu 1");
		ConsoleMenu menu2 = new RootMenu("Root Menu 2");

		menu1.addMenu(menu2);

		assertSame(menu1, menu1.getActiveMenu());
		menu1.setActiveMenu(1);
		assertSame(menu2, menu1.getActiveMenu());
	}

	@Test
	@DisplayName("RootMenu can exit to another RootMenu")
	void root_menu_test1() {
		ConsoleMenu menu1 = new RootMenu("Root Menu 1");
		ConsoleMenu menu2 = new RootMenu("Root Menu 2");

		menu1.addMenu(menu2);
		menu1.setActiveMenu(1);

		menu2.exitMenu();
		assertFalse(menu2.getExit());
		assertSame(menu1, menu1.getActiveMenu());
	}

	@Test
	@DisplayName("Active menu receives input")
	void active_menu_test() {
		ConsoleMenu menu1 = new RootMenu("Root Menu 1");
		menu1.setInputHandler(this);

		menu1.process(null, null, null);
		assertTrue(inputHandled);
	}

	@Test
	@DisplayName("Submenu receives input when active")
	void active_menu_test1() {
		ConsoleMenu menu1 = new RootMenu("Root Menu 1");
		ConsoleMenu menu2 = new SubMenu("Sub Menu");
		menu2.setInputHandler(this);

		menu1.addMenu(menu2);
		menu1.setActiveMenu(1);

		menu1.process(null, null,null);
		assertTrue(inputHandled);
	}

	@Test
	@DisplayName("Root menu without parent returns true for exit")
	void exit_root_menu_test() {
		ConsoleMenu menu = new RootMenu("Root Menu");
		menu.setExit(true);
		assertTrue(menu.getExit());
	}

	@Test
	@DisplayName("Trying to set non-active menu causes exception")
	void active_menu_exception() {
		try {
			ConsoleMenu menu = new RootMenu("Root Menu");
			menu.setActiveMenu(1);
		} catch (UnknownMenuException e) {
			assertEquals("Unknown menu for choice 1", e.getMessage());
		}
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		this.inputHandled = true;
	}
}
