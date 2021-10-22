package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

public class ConsoleRunner {
	private RootMenu rootMenu;
	private final InputStream in;
	private final PrintStream out;
	private final PrintStream err;
	boolean running;

	ConsoleRunner() {
		this(null, null);
	}

	public ConsoleRunner(InputStream in, PrintStream out) {
		this(in, out, null);
	}

	public ConsoleRunner(InputStream in, PrintStream out, PrintStream err) {
		this.in = in;
		this.out = out;
		this.err = err;
	}

	public void start() {
		running = true;
		if (out != null) {
			while (notDone()) {
				rootMenu.printMenu(out);
				rootMenu.process(new Scanner(in), out, err);
			}
		}
	}

	private boolean notDone() {
		return !rootMenu.getExit();
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
