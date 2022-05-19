package com.wodder.inventory.persistence;


import com.wodder.inventory.domain.model.*;

public interface PersistenceFactory {

	ProductRepository getInventoryDataStore();

	<T extends Entity<U>, U> Repository<T, U> getRepository(Class<T> clazz);
}
