package com.wodder.console.handlers;

import com.wodder.console.ConsoleMenu;
import java.io.PrintStream;
import java.util.Scanner;

public abstract class InputHandler {
  protected ConsoleMenu menu;

  protected InputHandler() {
  }

  public abstract void handleInput(Scanner input, PrintStream out, PrintStream err);

  public final void setMenu(ConsoleMenu menu) {
    this.menu = menu;
  }
}
