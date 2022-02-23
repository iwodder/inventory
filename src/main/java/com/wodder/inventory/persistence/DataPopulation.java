package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import javax.annotation.*;
import javax.ejb.*;

@Singleton
@Startup
public class DataPopulation {

	@PostConstruct
	void init() {
		PersistenceFactoryImpl impl = new PersistenceFactoryImpl();
		Repository<Location, LocationId> locationRepo = impl.getRepository(Location.class);
		locationRepo.createItem(new Location("Pantry"));
		locationRepo.createItem(new Location("Refrigerator"));
		locationRepo.createItem(new Location("Freezer"));
		locationRepo.createItem(new Location("Laundry Room"));

		Repository<Category, CategoryId> categoryRepo = impl.getRepository(Category.class);
		categoryRepo.createItem(new Category("Frozen"));
		categoryRepo.createItem(new Category("Dairy"));
		categoryRepo.createItem(new Category("Meats"));
		categoryRepo.createItem(new Category("Dry Goods"));
		categoryRepo.createItem(new Category("Chemicals"));

	}
}
