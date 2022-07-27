package com.wodder.console.menus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.console.ConsoleMenu;
import com.wodder.console.handlers.DefaultRootMenuHandler;
import com.wodder.console.handlers.InputHandler;
import com.wodder.console.menus.inventoryitems.BaseMenuTest;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    assertTrue(baosErr.toString().startsWith("Unknown menu for choice 1"));
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
    assertEquals(
        String.format("Unknown menu for choice 2%nPlease choose a valid menu%n"),
        baosErr.toString());
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
