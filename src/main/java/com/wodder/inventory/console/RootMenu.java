package com.wodder.inventory.console;

import java.io.*;
import java.util.*;

public class RootMenu extends ConsoleMenu {
	private final List<ConsoleMenu> subMenus;
	private ConsoleMenu activeMenu;

	public RootMenu(String name) {
		super(name);
		subMenus = new ArrayList<>();
		activeMenu = this;
	}

	@Override
	public void printMenu(PrintStream out) {
		if (activeMenu == this) {
			printRootMenu(out);
		} else {
			activeMenu.printMenu(out);
		}
	}

	private void printRootMenu(PrintStream out) {
		out.printf("====== %s ======%n", getMenuName());
		int menuCnt = 1;
		for (ConsoleMenu menu : subMenus) {
			out.printf("%d) %s%n", menuCnt++, menu.getMenuName());
		}
		out.printf("%d) Exit Menu%n", menuCnt);
	}

	@Override
	public void readInput(Scanner input) {
		activeMenu.handleInput(input, null, null);
	}

	@Override
	public void addMenu(ConsoleMenu menu) {
		menu.setParentMenu(this);
		subMenus.add(menu);
	}

	@Override
	public int subMenuCnt() {
		return subMenus.size();
	}

	@Override
	public void setActiveMenu(int menuId) {
		try {
			activeMenu = subMenus.get(menuId - 1);
		} catch (IndexOutOfBoundsException e) {
			throw new UnknownMenuException(String.format("Unknown menu for choice %d", menuId));
		}
	}

	@Override
	public ConsoleMenu getActiveMenu() {
		return activeMenu;
	}

	@Override
	public void setActiveMenu(ConsoleMenu menu) {
		this.activeMenu = menu;
	}
}
