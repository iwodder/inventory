package com.wodder.product.persistence;


import com.wodder.product.domain.model.Repository;
import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import com.wodder.product.domain.model.product.Price;
import com.wodder.product.domain.model.product.Product;
import com.wodder.product.domain.model.product.ProductId;
import com.wodder.product.domain.model.product.UnitOfMeasurement;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class DataPopulation {

  @PostConstruct
  public void init() {
    PersistenceFactoryImpl impl = new PersistenceFactoryImpl();

    Category c1 = new Category("Frozen");
    Category c2 = new Category("Dairy");
    Category c3 = new Category("Meats");
    Category c4 = new Category("Dry Goods");
    Category c5 = new Category("Chemicals");

    Repository<Category, CategoryId> categoryRepo = impl.getRepository(Category.class);
    categoryRepo.createItem(c1);
    categoryRepo.createItem(c2);
    categoryRepo.createItem(c3);
    categoryRepo.createItem(c4);
    categoryRepo.createItem(c5);

    Repository<Product, ProductId> productRepo = impl.getRepository(Product.class);
    productRepo.createItem(
        new Product(
            "2% Milk", c2, new UnitOfMeasurement("Gallons"), new Price("2.98")));
    productRepo.createItem(
        new Product(
            "Greek Yogurt", c2, new UnitOfMeasurement("Quarts"), new Price("1.99")));
    productRepo.createItem(
        new Product(
            "Ice", c2, new UnitOfMeasurement("Fluid Ounces"), new Price("0.99")));
    productRepo.createItem(
        new Product(
            "Pistachios", c4, new UnitOfMeasurement("Pounds"), new Price("10.29")));
  }
}
