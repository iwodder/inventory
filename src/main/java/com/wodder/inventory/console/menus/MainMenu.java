package com.wodder.inventory.console.menus;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.handlers.*;

public class MainMenu extends RootMenu {

	public MainMenu(InputHandler handler) {
		super("Main Menu", handler);
		handler.setMenu(this);
	}
}
