package com.wodder.console;

import com.wodder.console.handlers.InputHandler;
import java.io.PrintStream;

public class ExitMenu extends SubMenu {

  public ExitMenu(InputHandler handler) {
    super("Exit", handler);
  }

  public ExitMenu() {
    this(null);
  }

  @Override
  public void printMenu(PrintStream out) {
    out.print("Exiting...");
  }
}
