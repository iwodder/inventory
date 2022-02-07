package com.wodder.inventory.web.controllers;

import com.wodder.inventory.application.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import javax.annotation.*;
import javax.faces.view.*;
import javax.inject.*;
import java.io.*;
import java.util.*;

@Named
@ViewScoped
public class ItemsController implements Serializable {

	transient ItemService itemService;

	@PostConstruct
	void init() {
		itemService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(ItemService.class);
	}

	public List<InventoryItemModel> allItems() {
		return itemService.loadAllActiveItems();
	}

	public void onRowEdit() {
		System.out.println("Row edit invoked");
	}
}
