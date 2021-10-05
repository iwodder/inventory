package com.wodder.inventory.console;

public class SubMenu extends ConsoleMenu {

	SubMenu(String name) {
		super(name);
	}

	@Override
	public void exitMenu() {
		getParentMenu().exitMenu();
	}
}
