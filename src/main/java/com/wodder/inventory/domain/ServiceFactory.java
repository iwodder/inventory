package com.wodder.inventory.domain;

public interface ServiceFactory {

	<T> T getService(Class<T> service);
}
