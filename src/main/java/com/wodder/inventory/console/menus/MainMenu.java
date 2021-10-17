package com.wodder.inventory.console.menus;

import com.wodder.inventory.console.*;

import java.io.*;
import java.util.*;

public class MainMenu extends RootMenu implements InputHandler {

	public MainMenu() {
		super("Main Menu");
		setInputHandler(this);
	}

	@Override
	public void handleInput(Scanner input, PrintStream out, PrintStream err) {
		out.println("Please choose a menu");
		out.print("> ");
		try {
			setActiveMenu(input.nextInt());
		} catch (InputMismatchException ime) {
			err.println("Unrecognized input, please enter a number.");
		} catch (UnknownMenuException ume) {
			err.println(ume.getMessage());
			err.println("Please choose a valid menu");
		}
	}
}
