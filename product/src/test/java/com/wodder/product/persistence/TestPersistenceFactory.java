package com.wodder.product.persistence;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import com.wodder.product.domain.model.product.Entity;
import com.wodder.product.domain.model.product.ExternalId;
import com.wodder.product.domain.model.product.Price;
import com.wodder.product.domain.model.product.Product;
import com.wodder.product.domain.model.product.UnitOfMeasurement;

public class TestPersistenceFactory implements PersistenceFactory {
  private final InMemoryProductRepository productRepository;
  private final InMemoryCategoryRepository categoryRepository;
  private final InMemoryShipmentRepository shipmentRepository;

  private TestPersistenceFactory() {
    productRepository = new InMemoryProductRepository();
    categoryRepository = new InMemoryCategoryRepository();
    shipmentRepository = new InMemoryShipmentRepository();
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
  public ProductRepository getProductRepository() {
    return productRepository;
  }

  @Override
  public ShipmentRepository getShipmentRepository() {
    return shipmentRepository;
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

    Category c1 = new Category(CategoryId.categoryIdOf("c1"), "Frozen");
    Category c2 = new Category(CategoryId.categoryIdOf("c2"), "Dairy");
    Category c3 = new Category(CategoryId.categoryIdOf("c3"), "Meats");
    Category c4 = new Category(CategoryId.categoryIdOf("c4"), "Dry Goods");
    Category c5 = new Category(CategoryId.categoryIdOf("c5"), "Chemicals");

    categoryRepository.createItem(c1);
    categoryRepository.createItem(c2);
    categoryRepository.createItem(c3);
    categoryRepository.createItem(c4);
    categoryRepository.createItem(c5);

    productRepository.createItem(
        Product.from(ExternalId.of("item1"),
            "2% Milk", c2, new UnitOfMeasurement("Gallons"), new Price("2.98")));
    productRepository.createItem(
        Product.from(ExternalId.of("item2"),
            "Greek Yogurt", c2, new UnitOfMeasurement("Quarts"), new Price("1.99")));
    productRepository.createItem(
        Product.from(ExternalId.of("item3"),
            "Ice", c2, new UnitOfMeasurement("Fluid Ounces"), new Price("0.99")));
    productRepository.createItem(
        Product.from(ExternalId.of("item4"),
            "Pistachios", c4, new UnitOfMeasurement("Pounds"), new Price("10.29")));
  }
}
