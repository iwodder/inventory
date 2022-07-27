package com.wodder.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.console.exceptions.UnknownMenuException;
import com.wodder.console.handlers.DefaultRootMenuHandler;
import com.wodder.console.handlers.ExitHandler;
import com.wodder.console.handlers.InputHandler;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RootMenuTest extends InputHandler {
  private ByteArrayOutputStream baos;
  private ConsoleMenu menu;
  private boolean inputHandled = false;

  @BeforeEach
  void setup() {
    baos = new ByteArrayOutputStream();
    menu = new RootMenu("RootMenu", this);
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
    menu.addMenu(new ExitMenu());
    menu.printMenu(new PrintStream(baos));
    String output = baos.toString();
    String expected =
        String.format("====== RootMenu ======%n1) Exit%nPlease choose a menu entry.%n");
    assertEquals(expected, output);
  }

  @Test
  @DisplayName("RootMenu prints name, SubMenu title, and exit command")
  void print_menu_1() {
    menu.addMenu(new SubMenu("Inventory Item Menu"));
    menu.addMenu(new ExitMenu());
    menu.printMenu(new PrintStream(baos));
    String output = baos.toString();
    String expected =
        String.format(
            "====== RootMenu ======%n1) Inventory Item Menu%n2) Exit%nPlease choose a menu entry.%n");
    assertEquals(expected, output);
  }

  @Test
  @DisplayName("When RootMenu isn't active SubMenu prints")
  void print_menu_2() {
    ConsoleMenu testMenu =
        new SubMenu("Test Menu") {
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
  @DisplayName("Can add RootMenu to RootMenu")
  void root_menu_test() {
    ConsoleMenu menu1 = new RootMenu("Root Menu 1", null);
    ConsoleMenu menu2 = new RootMenu("Root Menu 2", null);

    menu1.addMenu(menu2);

    assertSame(menu1, menu1.getActiveMenu());
    menu1.setActiveMenu(1);
    assertSame(menu2, menu1.getActiveMenu());
  }

  @Test
  @DisplayName("RootMenu can exit to another RootMenu")
  void root_menu_test1() {
    ConsoleMenu menu1 = new RootMenu("Root Menu 1", null);
    ConsoleMenu menu2 = new RootMenu("Root Menu 2", null);

    menu1.addMenu(menu2);
    menu1.setActiveMenu(1);

    menu2.exitMenu();
    assertFalse(menu2.getExit());
    assertSame(menu1, menu1.getActiveMenu());
  }

  @Test
  @DisplayName("Active menu receives input")
  void active_menu_test() {
    ConsoleMenu menu1 = new RootMenu("Root Menu 1", null);
    menu1.setInputHandler(this);

    menu1.process(null, null, null);
    assertTrue(inputHandled);
  }

  @Test
  @DisplayName("Submenu receives input when active")
  void active_menu_test1() {
    ConsoleMenu menu1 = new RootMenu("Root Menu 1", null);
    ConsoleMenu menu2 = new SubMenu("Sub Menu");
    menu2.setInputHandler(this);

    menu1.addMenu(menu2);
    menu1.setActiveMenu(1);

    menu1.process(null, null, null);
    assertTrue(inputHandled);
  }

  @Test
  @DisplayName("Root menu without parent returns true for exit")
  void exit_root_menu_test() {
    ConsoleMenu menu = new RootMenu("Root Menu", null);

    menu.addMenu(new ExitMenu(new ExitHandler()));
    menu.setActiveMenu(1);
    menu.process(null, null, null);
    assertTrue(menu.getExit());
  }

  @Test
  @DisplayName("Trying to set non-active menu causes exception")
  void active_menu_exception() {
    try {
      ConsoleMenu menu = new RootMenu("Root Menu", null);
      menu.setActiveMenu(1);
    } catch (UnknownMenuException e) {
      assertEquals("Unknown menu for choice 1", e.getMessage());
    }
  }

  @Test
  @DisplayName("Root Menu passes control to Root Menu")
  void root_menu_control() {
    ConsoleMenu menu = new RootMenu("Root 1", new DefaultRootMenuHandler());
    ConsoleMenu menu1 = new RootMenu("Root 2", new DefaultRootMenuHandler());
    menu1.addMenu(new SubMenu("Sub Menu 1", this));
    menu.addMenu(menu1);
    menu.setActiveMenu(1);
    menu1.setActiveMenu(1);
    menu.process(null, null, null);
    assertTrue(inputHandled);
  }

  @Test
  @DisplayName("Root Menu passes control to Root Menu")
  void root_menu_printing() {
    ConsoleMenu menu = new RootMenu("Root 1", new DefaultRootMenuHandler());
    ConsoleMenu menu1 = new RootMenu("Root 2", new DefaultRootMenuHandler());
    menu1.addMenu(new SubMenu("Sub Menu 1", this));
    menu.addMenu(menu1);
    menu.setActiveMenu(1);
    menu1.setActiveMenu(1);
    menu.process(null, null, null);
    menu.printMenu(new PrintStream(baos));
    assertEquals(String.format("====== Sub Menu 1 ======%n"), baos.toString());
  }

  @Override
  public void handleInput(Scanner input, PrintStream out, PrintStream err) {
    this.inputHandled = true;
  }
}
