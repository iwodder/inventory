package com.wodder.console.handlers;

import com.wodder.console.exceptions.UnknownMenuException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DefaultRootMenuHandler extends InputHandler {

  @Override
  public void handleInput(Scanner input, PrintStream out, PrintStream err) {
    try {
      menu.setActiveMenu(input.nextInt());
    } catch (InputMismatchException ime) {
      err.println("Unrecognized input, please enter a number.");
    } catch (UnknownMenuException ume) {
      err.println(ume.getMessage());
      err.println("Please choose a valid menu");
    }
  }
}
