package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

public class ConsoleRunner {
	private final static String INPUT_PROMPT = "> ";
	private ConsoleMenu rootMenu;
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
				try {
					rootMenu.printMenu(out);
					out.print(INPUT_PROMPT);
					rootMenu.process(new Scanner(in), out, err);
				} catch (Exception e) {
					printErrorMsg(e);
				}
			}
		}
	}

	private void printErrorMsg(Exception e) {
		String msg = e.getMessage();
		if (msg != null && !msg.isEmpty()) {
			err.println(msg);
		} else {
			err.println("Unknown problem occurred during processing.");
		}
	}

	private boolean notDone() {
		return !rootMenu.getExit();
	}

	public ConsoleMenu getMenu() {
		return rootMenu;
	}

	public void setMenu(ConsoleMenu menu) {
		this.rootMenu = menu;
	}

	public InputStream getInput() {
		return in;
	}

	public PrintStream getOutput() {
		return out;
	}
}
