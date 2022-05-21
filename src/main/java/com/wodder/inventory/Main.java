package com.wodder.inventory;

import com.wodder.inventory.application.implementations.ServiceFactoryImpl;
import com.wodder.inventory.console.ConsoleRunner;
import com.wodder.inventory.console.menus.MenuFactory;
import com.wodder.inventory.persistence.PersistenceFactoryImpl;

public class Main {

  public static void main(String[] args) {
    System.out.println("Welcome to your inventory");
    MenuFactory menus = new MenuFactory(new ServiceFactoryImpl(new PersistenceFactoryImpl()));
    ConsoleRunner runner = new ConsoleRunner(System.in, System.out, System.err);
    runner.setMenu(menus.createMainMenu());
    runner.start();
  }
}
