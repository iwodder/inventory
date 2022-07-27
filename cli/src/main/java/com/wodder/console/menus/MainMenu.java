package com.wodder.console.menus;

import com.wodder.console.RootMenu;
import com.wodder.console.handlers.InputHandler;

public class MainMenu extends RootMenu {

  public MainMenu(InputHandler handler) {
    super("Main Menu", handler);
    handler.setMenu(this);
  }
}
