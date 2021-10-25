package com.wodder.inventory;

import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.*;

public class Main {


	public static void main(String[] args) {
		System.out.println("Welcome to your inventory");
		MenuFactory menus = new MenuFactory();
		ConsoleRunner runner = new ConsoleRunner(System.in, System.out, System.err);
		runner.setRootMenu(menus.createMainMenu());
		runner.start();
	}
}
