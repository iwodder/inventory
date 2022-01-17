package com.wodder.inventory.web.controllers;

import com.wodder.inventory.application.*;
import com.wodder.inventory.models.*;

import javax.faces.view.*;
import javax.inject.*;
import java.io.*;
import java.util.*;

@Named
@ViewScoped
public class ItemsController implements Serializable {

	@Inject
	ItemStorage itemStorage;

	public List<InventoryItemModel> allItems() {
		return itemStorage.loadAllActiveItems();
	}

	public void onRowEdit() {
		System.out.println("Row edit invoked");
	}
}
