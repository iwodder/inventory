package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

public class ConsoleRunner {
	private RootMenu rootMenu;
	private final InputStream in;
	private final PrintStream out;
	boolean running;

	ConsoleRunner() {
		this(null, null);
	}

	public ConsoleRunner(InputStream in, PrintStream out) {
		this.in = in;
		this.out = out;
	}

	public void start() {
		running = true;
		if (out != null) {
			while (notDone()) {
				rootMenu.printMenu(out);
				rootMenu.handleInput(new Scanner(in), out, null);
			}
		}
	}

	private boolean notDone() {
		return !rootMenu.exitMenu();
	}

	public RootMenu getRootMenu() {
		return rootMenu;
	}

	public void setRootMenu(RootMenu menu) {
		this.rootMenu = menu;
	}

	public InputStream getInput() {
		return in;
	}

	public PrintStream getOutput() {
		return out;
	}
}
