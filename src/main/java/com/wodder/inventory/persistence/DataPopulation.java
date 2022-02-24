package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import javax.annotation.*;
import javax.ejb.*;

@Singleton
@Startup
public class DataPopulation {

	@PostConstruct
	void init() {
		Location l1 = new Location("Pantry");
		Location l2 = new Location("Refrigerator");
		Location l3 = new Location("Freezer");
		Location l4 = new Location("Laundry Room");

		PersistenceFactoryImpl impl = new PersistenceFactoryImpl();
		Repository<Location, LocationId> locationRepo = impl.getRepository(Location.class);
		locationRepo.createItem(l1);
		locationRepo.createItem(l2);
		locationRepo.createItem(l3);
		locationRepo.createItem(l4);

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

	}
}
