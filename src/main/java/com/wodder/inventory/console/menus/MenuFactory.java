package com.wodder.inventory.console.menus;

import com.wodder.inventory.application.ProductService;
import com.wodder.inventory.application.ServiceFactory;
import com.wodder.inventory.console.ConsoleMenu;
import com.wodder.inventory.console.ExitMenu;
import com.wodder.inventory.console.RootMenu;
import com.wodder.inventory.console.handlers.CreateItemHandler;
import com.wodder.inventory.console.handlers.DefaultRootMenuHandler;
import com.wodder.inventory.console.handlers.DeleteItemHandler;
import com.wodder.inventory.console.handlers.ExitHandler;
import com.wodder.inventory.console.handlers.UpdateItemHandler;
import com.wodder.inventory.console.handlers.ViewItemHandler;
import com.wodder.inventory.console.menus.inventoryitems.CreateItemMenu;
import com.wodder.inventory.console.menus.inventoryitems.DeleteItemMenu;
import com.wodder.inventory.console.menus.inventoryitems.InventoryItemMenu;
import com.wodder.inventory.console.menus.inventoryitems.UpdateItemMenu;
import com.wodder.inventory.console.menus.inventoryitems.ViewItemsMenu;
import com.wodder.inventory.console.models.InventoryItemModel;
import com.wodder.inventory.console.models.InventoryItemModelImpl;

public class MenuFactory {
  private final ServiceFactory serviceFactory;

  public MenuFactory(ServiceFactory serviceFactory) {
    this.serviceFactory = serviceFactory;
  }

  public ConsoleMenu createMainMenu() {
    RootMenu main = new MainMenu(new DefaultRootMenuHandler());
    InventoryItemMenu inventoryItemMenu = createInventoryMenu();
    main.addMenu(inventoryItemMenu);
    main.addMenu(new ExitMenu(new ExitHandler()));
    return main;
  }

  private InventoryItemMenu createInventoryMenu() {
    InventoryItemMenu inventoryItemMenu = new InventoryItemMenu(new DefaultRootMenuHandler());
    InventoryItemModel model =
        new InventoryItemModelImpl(serviceFactory.getService(ProductService.class));
    inventoryItemMenu.addMenu(new CreateItemMenu(new CreateItemHandler(model), model));
    inventoryItemMenu.addMenu(new DeleteItemMenu(new DeleteItemHandler(model)));
    inventoryItemMenu.addMenu(new UpdateItemMenu(new UpdateItemHandler(model)));
    inventoryItemMenu.addMenu(new ViewItemsMenu(new ViewItemHandler(model)));
    inventoryItemMenu.addMenu(new ExitMenu(new ExitHandler()));
    return inventoryItemMenu;
  }
}
