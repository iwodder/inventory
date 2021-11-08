package com.wodder.inventory.console.handlers;

import com.wodder.inventory.console.*;

import java.io.*;
import java.util.*;

public abstract class InputHandler {
	protected ConsoleMenu menu;

	protected InputHandler() {}

	public abstract void handleInput(Scanner input, PrintStream out, PrintStream err);

	public final void setMenu(ConsoleMenu menu) {
		this.menu = menu;
	}
}
