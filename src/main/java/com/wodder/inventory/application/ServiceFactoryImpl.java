package com.wodder.inventory.application;

import com.wodder.inventory.persistence.*;

public class ServiceFactoryImpl implements ServiceFactory {
	private PersistenceFactory factory;

	public ServiceFactoryImpl() {}

	public ServiceFactoryImpl(PersistenceFactory factory) {
		this.factory = factory;
	}

	public ItemStorage getItemStorage() {
		return new ItemStorageService();
	}

	@Override
	public <T> T getService(Class<T> service) {
		try {
			if (service.isAssignableFrom(ItemStorageService.class)) {
				return (T) new ItemStorageService(factory.getInventoryDataStore(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
