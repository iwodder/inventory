package com.wodder.inventory.application;

public interface ServiceFactory {

	<T> T getService(Class<T> service);
}
