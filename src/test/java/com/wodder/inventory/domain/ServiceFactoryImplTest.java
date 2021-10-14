package com.wodder.inventory.domain;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ServiceFactoryImplTest {


	@Test
	@DisplayName("Can provide the ItemStorageService")
	void item_service() {
		ItemStorage itemStorage = new ServiceFactoryImpl().getItemStorage();
		assertNotNull(itemStorage);
	}

	@Test
	@DisplayName("Can access service class through interface")
	void service_interface() {
		ItemStorage itemStorage = new ServiceFactoryImpl().getService(ItemStorage.class);
		assertNotNull(itemStorage);
	}
}
