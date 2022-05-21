package com.wodder.inventory.console.menus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.inventory.application.implementations.ServiceFactoryImpl;
import com.wodder.inventory.console.ConsoleMenu;
import com.wodder.inventory.console.RootMenu;
import com.wodder.inventory.console.menus.inventoryitems.InventoryItemMenu;
import com.wodder.inventory.persistence.PersistenceFactoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuFactoryTest {

  @Test
  @DisplayName("Creates a new CLI menu")
  void create_menu() {
    MenuFactory menus = new MenuFactory(new ServiceFactoryImpl(new PersistenceFactoryImpl()));
    ConsoleMenu mainMenu = menus.createMainMenu();
    assertNotNull(mainMenu);
    assertEquals("Main Menu", mainMenu.getMenuName());
    assertInstanceOf(RootMenu.class, mainMenu);
  }

  @Test
  @DisplayName("Main Menu contains Inventory Item Menu")
  void main_menu_contains_inventory_menu() {
    MenuFactory menus = new MenuFactory(new ServiceFactoryImpl());
    ConsoleMenu menu = menus.createMainMenu();
    assertTrue(menu.containsMenu(InventoryItemMenu.class));
  }
}
