package com.wodder.inventory.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ServiceFactoryTest {


	@Test
	@DisplayName("Can provide the ItemStorageService")
	void item_service() {
		ItemStorage itemStorage = ServiceFactory.getItemStorage();
		assertNotNull(itemStorage);
	}

	@Test
	@DisplayName("Can access service class through interface")
	void service_interface() {
		ItemStorage itemStorage = ServiceFactory.getService(ItemStorage.class);
		assertNotNull(itemStorage);
	}
}
