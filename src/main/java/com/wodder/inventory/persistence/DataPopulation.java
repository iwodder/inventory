package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import javax.annotation.*;
import javax.ejb.*;

@Singleton
@Startup
public class DataPopulation {

	@PostConstruct
	public void init() {
		initForTesting(new PersistenceFactoryImpl());
	}

	public void initForTesting(PersistenceFactory factory) {
		Location l1 = new Location("Pantry");
		Location l2 = new Location("Refrigerator");
		Location l3 = new Location("Freezer");
		Location l4 = new Location("Laundry Room");

		Repository<Location, LocationId> locationRepo = factory.getRepository(Location.class);
		locationRepo.createItem(l1);
		locationRepo.createItem(l2);
		locationRepo.createItem(l3);
		locationRepo.createItem(l4);

		Category c1 = new Category("Frozen");
		Category c2 = new Category("Dairy");
		Category c3 = new Category("Meats");
		Category c4 = new Category("Dry Goods");
		Category c5 = new Category("Chemicals");

		Repository<Category, CategoryId> categoryRepo = factory.getRepository(Category.class);
		categoryRepo.createItem(c1);
		categoryRepo.createItem(c2);
		categoryRepo.createItem(c3);
		categoryRepo.createItem(c4);
		categoryRepo.createItem(c5);

		Repository<Product, ProductId> productRepo = factory.getRepository(Product.class);
		productRepo.createItem(new Product("2% Milk", c2, l2, new UnitOfMeasurement("Gallons", 2), new Price("2.98", "5.96")));
		productRepo.createItem(new Product("Greek Yogurt", c2, l2, new UnitOfMeasurement("Quarts", 2), new Price("1.99", "4.98")));
		productRepo.createItem(new Product("Ice", c2, l2, new UnitOfMeasurement("Fluid Ounces", 12), new Price("0.99", "10.99")));
		productRepo.createItem(new Product("Pistachios", c4, l1, new UnitOfMeasurement("Pounds", 1), new Price("10.29", "10.29")));
	}
}
