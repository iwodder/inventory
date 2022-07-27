package com.wodder.console.menus;

import com.wodder.console.ConsoleMenu;
import com.wodder.console.ExitMenu;
import com.wodder.console.RootMenu;
import com.wodder.console.handlers.CreateItemHandler;
import com.wodder.console.handlers.DefaultRootMenuHandler;
import com.wodder.console.handlers.DeleteItemHandler;
import com.wodder.console.handlers.ExitHandler;
import com.wodder.console.handlers.UpdateItemHandler;
import com.wodder.console.handlers.ViewItemHandler;
import com.wodder.console.menus.inventoryitems.CreateItemMenu;
import com.wodder.console.menus.inventoryitems.DeleteItemMenu;
import com.wodder.console.menus.inventoryitems.InventoryItemMenu;
import com.wodder.console.menus.inventoryitems.UpdateItemMenu;
import com.wodder.console.menus.inventoryitems.ViewItemsMenu;
import com.wodder.console.models.InventoryItemModel;
import com.wodder.console.models.InventoryItemModelImpl;
import com.wodder.inventory.application.ServiceFactory;
import com.wodder.product.application.ProductService;

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
