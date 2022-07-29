package com.wodder.product.persistence;

import com.wodder.product.domain.model.product.Category;
import com.wodder.product.domain.model.product.Entity;
import com.wodder.product.domain.model.product.Price;
import com.wodder.product.domain.model.product.Product;
import com.wodder.product.domain.model.product.UnitOfMeasurement;

public class TestPersistenceFactory implements PersistenceFactory {
  private final InMemoryProductRepository productRepository;
  private final InMemoryCategoryRepository categoryRepository;

  private TestPersistenceFactory() {
    productRepository = new InMemoryProductRepository();
    categoryRepository = new InMemoryCategoryRepository();
  }

  public static PersistenceFactory getUnpopulated() {
    return new TestPersistenceFactory();
  }

  public static PersistenceFactory getPopulated() {
    TestPersistenceFactory t = new TestPersistenceFactory();
    t.populateWithData();
    return t;
  }

  @Override
  public ProductRepository getInventoryDataStore() {
    return productRepository;
  }

  @Override
  public <T extends Entity<U>, U> Repository<T, U> getRepository(Class<T> clazz) {
    if (clazz.isAssignableFrom(Category.class)) {
      @SuppressWarnings("unchecked")
      Repository<T, U> c = (Repository<T, U>) categoryRepository;
      return c;
    } else if (clazz.isAssignableFrom(Product.class)) {
      @SuppressWarnings("unchecked")
      Repository<T, U> c = (Repository<T, U>) productRepository;
      return c;
    }
    return null;
  }

  private void populateWithData() {

    Category c1 = new Category("Frozen");
    Category c2 = new Category("Dairy");
    Category c3 = new Category("Meats");
    Category c4 = new Category("Dry Goods");
    Category c5 = new Category("Chemicals");

    categoryRepository.createItem(c1);
    categoryRepository.createItem(c2);
    categoryRepository.createItem(c3);
    categoryRepository.createItem(c4);
    categoryRepository.createItem(c5);

    productRepository.createItem(
        new Product(
            "2% Milk", c2, new UnitOfMeasurement("Gallons", 2), new Price("2.98", "5.96")));
    productRepository.createItem(
        new Product(
            "Greek Yogurt", c2, new UnitOfMeasurement("Quarts", 2), new Price("1.99", "4.98")));
    productRepository.createItem(
        new Product(
            "Ice", c2, new UnitOfMeasurement("Fluid Ounces", 12), new Price("0.99", "10.99")));
    productRepository.createItem(
        new Product(
            "Pistachios", c4, new UnitOfMeasurement("Pounds", 1), new Price("10.29", "10.29")));
  }
}
