package com.wodder.inventory.domain;

public class ServiceFactoryImpl implements ServiceFactory {

	public ServiceFactoryImpl() {}

	public ItemStorage getItemStorage() {
		return new ItemStorageService();
	}

	@Override
	public <T> T getService(Class<T> service) {
		try {
			if (service.isAssignableFrom(ItemStorageService.class)) {
				return (T) new ItemStorageService();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
