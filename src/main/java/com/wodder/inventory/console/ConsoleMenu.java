package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

public class ConsoleMenu {
	private final String menuName;
	private ConsoleMenu parentMenu;

	ConsoleMenu(String name) {
		menuName = name;
	}

	public void printMenu(PrintStream out) {
		throw new UnsupportedOperationException(getUnsupportedOpMsg("printMenu"));
	}

	public void readInput(Scanner input) {
		throw new UnsupportedOperationException(getUnsupportedOpMsg("readInput"));
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

	public String getMenuName() {
		return menuName;
	}

	private String getUnsupportedOpMsg(String method) {
		return String.format("%s not defined for %s", method, getClass().getName());
	}
}
