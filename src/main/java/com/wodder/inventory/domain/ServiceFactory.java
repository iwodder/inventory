package com.wodder.inventory.domain;

public class ServiceFactory {

	private ServiceFactory() {
		//no-op
	}

	public static ItemStorage getItemStorage() {
		return new ItemStorageService();
	}

	public static <T> T getService(Class<T> service) {
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
