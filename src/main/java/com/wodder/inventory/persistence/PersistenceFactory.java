package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

public interface PersistenceFactory {

	ProductRepository getInventoryDataStore();

	<T extends Entity> Repository<T> getRepository(Class<T> clazz);
}
