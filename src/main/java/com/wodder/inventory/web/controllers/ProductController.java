package com.wodder.inventory.web.controllers;

import com.wodder.inventory.application.*;
import com.wodder.inventory.application.implementations.*;
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
public class ProductController implements Serializable {

	private ProductModel model;
	private CategoryModel categoryModel;
	private LocationModel locationModel;

	transient ItemService itemService;
	transient CategoryService categoryService;
	transient LocationService locationService;

	@PostConstruct
	void init() {
		itemService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(ItemService.class);
		categoryService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(CategoryService.class);
		locationService = new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(LocationService.class);
	}

	public List<ProductModel> allItems() {
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
		this.model = new ProductModel();
	}

	public void newCategory() {
		this.categoryModel = new CategoryModel();
	}

	public void newLocation() {
		this.locationModel = new LocationModel();
	}

	public void saveProduct() {
		Optional<ProductModel> item = itemService.createNewItem(
				model.getName(), model.getCategory(), model.getLocation(), model.getUnits(), model.getUnitsPerCase(),
				model.getItemPrice(), model.getCasePrice());
		if (item.isPresent()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product saved successfully."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product not saved."));
		}

		PrimeFaces.current().executeScript("PF('addProduct').hide()");
		PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
	}

	public void saveCategory() {
		Optional<CategoryModel> category = categoryService.createCategory(categoryModel.getName());

		if (category.isPresent()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category saved successfully."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category not saved."));
		}

		PrimeFaces.current().executeScript("PF('addCategory').hide()");
		PrimeFaces.current().ajax().update("form:messages");
	}

	public void saveLocation() {
		Optional<LocationModel> category = locationService.createLocation(locationModel.getName());

		if (category.isPresent()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Location saved successfully."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Location not saved."));
		}

		PrimeFaces.current().executeScript("PF('addLocation').hide()");
		PrimeFaces.current().ajax().update("form:messages");
	}

	public ProductModel getModel() {
		return model;
	}

	public void setModel(ProductModel model) {
		this.model = model;
	}

	public CategoryModel getCategoryModel() {
		return categoryModel;
	}

	public void setCategoryModel(CategoryModel categoryModel) {
		this.categoryModel = categoryModel;
	}

	public LocationModel getLocationModel() {
		return locationModel;
	}

	public void setLocationModel(LocationModel locationModel) {
		this.locationModel = locationModel;
	}
}
