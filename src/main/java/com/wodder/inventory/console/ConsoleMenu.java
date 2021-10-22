package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

public class ConsoleMenu {
	private final String menuName;
	private ConsoleMenu parentMenu;
	private InputHandler inputHandler;
	private boolean exit = false;

	public ConsoleMenu(String name) {
		menuName = name;
	}

	public void printMenu(PrintStream out) {
		throw new UnsupportedOperationException(getUnsupportedOpMsg("printMenu"));
	}

	public void addMenu(ConsoleMenu menu) {
		throw new UnsupportedOperationException(getUnsupportedOpMsg("addMenu"));
	}

	public int subMenuCnt() {
		throw new UnsupportedOperationException(getUnsupportedOpMsg("subMenuCnt"));
	}

	public void setActiveMenu(int menuId) {
		throw new UnsupportedOperationException(getUnsupportedOpMsg("setActiveMenu"));
	}

	public void setActiveMenu(ConsoleMenu menu) {
		throw new UnsupportedOperationException();
	}

	public void setParentMenu(ConsoleMenu menu) {
		parentMenu = menu;
	}

	public ConsoleMenu getParentMenu() {
		return parentMenu;
	}

	public ConsoleMenu getActiveMenu() {
		throw new UnsupportedOperationException(getUnsupportedOpMsg("getActiveMenu"));
	}

	public void exitMenu() {
		throw new UnsupportedOperationException(getUnsupportedOpMsg("exitMenu"));
	}

	public boolean getExit() {
		return exit;
	}

	protected void setExit(boolean exit) {
		this.exit = exit;
	}

	public void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public void process(Scanner in, PrintStream out, PrintStream err) {
		inputHandler.handleInput(in, out, err);
	}

	public String getMenuName() {
		return menuName;
	}

	private String getUnsupportedOpMsg(String method) {
		return String.format("%s not defined for %s", method, getClass().getName());
	}
}
