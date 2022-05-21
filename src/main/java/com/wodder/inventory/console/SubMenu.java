package com.wodder.inventory.console;

import com.wodder.inventory.console.handlers.InputHandler;
import java.io.PrintStream;

public class SubMenu extends ConsoleMenu {

  public SubMenu(String name) {
    this(name, null);
  }

  public SubMenu(String name, InputHandler handler) {
    super(name, handler);
    if (handler != null) {
      handler.setMenu(this);
    }
  }

  @Override
  public void printMenu(PrintStream out) {
    out.printf("====== %s ======%n", getMenuName());
  }

  @Override
  public void exitMenu() {
    if (getParentMenu() != null) {
      getParentMenu().setActiveMenu(getParentMenu());
    }
  }
}
