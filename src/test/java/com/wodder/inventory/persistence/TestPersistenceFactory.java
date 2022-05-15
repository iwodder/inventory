package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

public class TestPersistenceFactory implements PersistenceFactory {
	private final InMemoryProductRepository productRepository;
	private final InMemoryCategoryRepository categoryRepository;
	private final InMemoryLocationRepository locationRepository;
	private final InMemoryInventoryRepository inventoryRepository;

	private TestPersistenceFactory() {
		productRepository = new InMemoryProductRepository();
		categoryRepository = new InMemoryCategoryRepository();
		locationRepository = new InMemoryLocationRepository();
		inventoryRepository = new InMemoryInventoryRepository();
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
			Repository<T, U> c =  (Repository<T, U>) categoryRepository;
			return c;
		} else if (clazz.isAssignableFrom(Location.class)) {
			@SuppressWarnings("unchecked")
			Repository<T, U> c =  (Repository<T, U>) locationRepository;
			return c;
		} else if (clazz.isAssignableFrom(Inventory.class)) {
			@SuppressWarnings("unchecked")
			Repository<T, U> c =  (Repository<T, U>) inventoryRepository;
			return c;
		} else if (clazz.isAssignableFrom(Product.class)) {
			@SuppressWarnings("unchecked")
			Repository<T, U> c =  (Repository<T, U>) productRepository;
			return c;
		}
		return null;
	}

	private void populateWithData() {
		Location l1 = new Location("Pantry");
		Location l2 = new Location("Refrigerator");
		Location l3 = new Location("Freezer");
		Location l4 = new Location("Laundry Room");

		locationRepository.createItem(l1);
		locationRepository.createItem(l2);
		locationRepository.createItem(l3);
		locationRepository.createItem(l4);

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

		productRepository.createItem(new Product("2% Milk", c2, l2, new UnitOfMeasurement("Gallons", 2), new Price("2.98", "5.96")));
		productRepository.createItem(new Product("Greek Yogurt", c2, l2, new UnitOfMeasurement("Quarts", 2), new Price("1.99", "4.98")));
		productRepository.createItem(new Product("Ice", c2, l2, new UnitOfMeasurement("Fluid Ounces", 12), new Price("0.99", "10.99")));
		productRepository.createItem(new Product("Pistachios", c4, l1, new UnitOfMeasurement("Pounds", 1), new Price("10.29", "10.29")));
	}
}
