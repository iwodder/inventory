package com.wodder.inventory.web.controllers;

import com.wodder.inventory.application.CategoryService;
import com.wodder.inventory.application.LocationService;
import com.wodder.inventory.application.ProductService;
import com.wodder.inventory.application.implementations.ServiceFactoryImpl;
import com.wodder.inventory.dto.CategoryModel;
import com.wodder.inventory.dto.LocationModel;
import com.wodder.inventory.dto.ProductDto;
import com.wodder.inventory.persistence.PersistenceFactoryImpl;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

@Named
@ViewScoped
public class ProductController implements Serializable {
  private static final long serialVersionUID = 1L;
  transient ProductService productService;
  transient CategoryService categoryService;
  transient LocationService locationService;
  private ProductDto model;
  private CategoryModel categoryModel;
  private LocationModel locationModel;

  @PostConstruct
  void init() {
    productService =
        new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(ProductService.class);
    categoryService =
        new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(CategoryService.class);
    locationService =
        new ServiceFactoryImpl(new PersistenceFactoryImpl()).getService(LocationService.class);
  }

  public List<ProductDto> allItems() {
    return productService.loadAllActiveProducts();
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
    this.model = new ProductDto();
  }

  public void newCategory() {
    this.categoryModel = new CategoryModel();
  }

  public void newLocation() {
    this.locationModel = new LocationModel();
  }

  public void saveProduct() {
    Optional<ProductDto> item =
        productService.createNewProduct(
            model.getName(),
            model.getCategory(),
            model.getLocation(),
            model.getUnits(),
            model.getUnitsPerCase(),
            model.getItemPrice(),
            model.getCasePrice());
    if (item.isPresent()) {
      FacesContext.getCurrentInstance()
          .addMessage(null, new FacesMessage("Product saved successfully."));
    } else {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product not saved."));
    }

    PrimeFaces.current().executeScript("PF('addProduct').hide()");
    PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
  }

  public void saveCategory() {
    Optional<CategoryModel> category = categoryService.createCategory(categoryModel.getName());

    if (category.isPresent()) {
      FacesContext.getCurrentInstance()
          .addMessage(null, new FacesMessage("Category saved successfully."));
    } else {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category not saved."));
    }

    PrimeFaces.current().executeScript("PF('addCategory').hide()");
    PrimeFaces.current().ajax().update("form:messages");
  }

  public void saveLocation() {
    Optional<LocationModel> category = locationService.createLocation(locationModel.getName());

    if (category.isPresent()) {
      FacesContext.getCurrentInstance()
          .addMessage(null, new FacesMessage("Location saved successfully."));
    } else {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Location not saved."));
    }

    PrimeFaces.current().executeScript("PF('addLocation').hide()");
    PrimeFaces.current().ajax().update("form:messages");
  }

  public ProductDto getModel() {
    return model;
  }

  public void setModel(ProductDto model) {
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
