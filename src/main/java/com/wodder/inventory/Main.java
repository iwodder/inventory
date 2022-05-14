package com.wodder.inventory;

import com.wodder.inventory.application.implementations.*;
import com.wodder.inventory.console.*;
import com.wodder.inventory.console.menus.*;
import com.wodder.inventory.persistence.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to your inventory");
		MenuFactory menus = new MenuFactory(new ServiceFactoryImpl(new PersistenceFactoryImpl()));
		ConsoleRunner runner = new ConsoleRunner(System.in, System.out, System.err);
		runner.setMenu(menus.createMainMenu());
		runner.start();
	}
}
