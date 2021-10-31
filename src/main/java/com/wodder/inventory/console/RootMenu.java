package com.wodder.inventory.console;

import com.wodder.inventory.console.exceptions.*;
import com.wodder.inventory.console.handlers.*;

import java.io.*;
import java.util.*;

public class RootMenu extends ConsoleMenu {
	private final List<ConsoleMenu> subMenus;
	private ConsoleMenu activeMenu;

	public RootMenu(String name, InputHandler handler) {
		super(name, handler);
		subMenus = new ArrayList<>();
		setActiveMenu(this);
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
	}

	@Override
	public void process(Scanner in, PrintStream out, PrintStream err) {
		if (activeMenu == this) {
			getInputHandler().handleInput(in,out,err);
		} else {
			activeMenu.process(in, out, err);
		}
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
		int idx = menuId - 1;
		if (idx >= 0 && idx < subMenuCnt()) {
			activeMenu = subMenus.get(idx);
		} else {
			throw new UnknownMenuException(String.format("Unknown menu for choice %d", menuId));
		}
	}

	@Override
	public void exitMenu() {
		if (getParentMenu() != null) {
			activateParentMenu();
		} else {
			activeMenu = this;
			setExit(true);
		}
	}

	private void activateParentMenu() {
		getParentMenu().setActiveMenu(getParentMenu());
	}

	@Override
	public ConsoleMenu getActiveMenu() {
		return activeMenu;
	}

	@Override
	public void setActiveMenu(ConsoleMenu menu) {
		this.activeMenu = menu;
	}

	@Override
	public boolean containsMenu(Class<? extends ConsoleMenu> menu) {
		boolean contained = false;
		for (ConsoleMenu m : subMenus) {
			if (m.getClass().equals(menu)) {
				contained = true;
				break;
			}
		}
		return contained;
	}
}
