package com.wodder.inventory.web.controllers;

import com.wodder.inventory.application.ProductService;
import com.wodder.inventory.application.implementations.ServiceFactoryImpl;
import com.wodder.inventory.application.inventory.InventoryService;
import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.dto.InventoryItemDto;
import com.wodder.inventory.persistence.PersistenceFactoryImpl;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class InventoryController implements Serializable {
  private static final long serialVersionUID = 1L;
  private transient InventoryService inventoryService;
  private transient ProductService productService;
  private Inventory model;
  private Map<String, List<InventoryItemDto>> items;

  @PostConstruct
  void init() {
    inventoryService =
        new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(InventoryService.class);
    productService =
        new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(ProductService.class);
    model = inventoryService.createInventory();

  }

  public String getInventoryDate() {
    return model.getInventoryDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
  }

  public Map<String, List<InventoryItemDto>> getItemMap() {
    return items;
  }

  public List<InventoryItemDto> getItems(String location) {
    return Collections.unmodifiableList(items.getOrDefault(location, Collections.emptyList()));
  }

  public List<String> getLocations() {
    return new ArrayList<>(items.keySet());
  }

  public void save() {
    //inventoryService.saveInventory(model);
  }
}
