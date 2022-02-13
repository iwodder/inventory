package com.wodder.inventory.web.controllers;

import com.wodder.inventory.application.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;
import org.primefaces.*;

import javax.annotation.*;
import javax.faces.application.*;
import javax.faces.context.*;
import javax.faces.view.*;
import javax.inject.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

@Named
@ViewScoped
public class ItemsController implements Serializable {

	private InventoryItemModel model;

	transient ItemService itemService;
	transient CategoryService categoryService;
	transient LocationService locationService;

	@PostConstruct
	void init() {
		itemService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(ItemService.class);
		categoryService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(CategoryService.class);
		locationService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(LocationService.class);
	}

	public List<InventoryItemModel> allItems() {
		return itemService.loadAllActiveItems();
	}

	public List<String> completeCategory(String category) {
		List<CategoryModel> categories = categoryService.loadCategories();
		return categories.stream()
				.map(CategoryModel::getName)
				.filter(s -> s.contains(category))
				.collect(Collectors.toList());
	}

	public List<String> completeLocation(String location) {
		List<LocationModel> categories = locationService.loadLocations();
		return categories.stream()
				.map(LocationModel::getName)
				.filter(s -> s.contains(location))
				.collect(Collectors.toList());
	}

	public void newItem() {
		this.model = new InventoryItemModel();
	}

	public void saveProduct() {
		Optional<InventoryItemModel> item = itemService.createNewItem(
				model.getName(), model.getCategory(), model.getLocation(), model.getUnits(), model.getUnitsPerCase(),
				model.getItemPrice(), model.getCasePrice());
		if (item.isPresent()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item saved successfully."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item not saved."));
		}

		PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
 	}

	public InventoryItemModel getModel() {
		return model;
	}

	public void setModel(InventoryItemModel model) {
		this.model = model;
	}
}
