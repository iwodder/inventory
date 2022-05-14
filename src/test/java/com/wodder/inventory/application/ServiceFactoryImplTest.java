package com.wodder.inventory.application;

import com.wodder.inventory.application.implementations.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceFactoryImplTest {

	@Mock
	PersistenceFactory factory;

	@InjectMocks
	ServiceFactoryImpl impl;

	@Test
	@DisplayName("Can access service class through interface")
	void service_interface() {
		ItemService itemService = impl.getService(ItemService.class);
		assertNotNull(itemService);
	}

	@Test
	@DisplayName("Can load the Inventory Service")
	void inventory_service() {
		InventoryService svc = impl.getService(InventoryService.class);
		assertNotNull(svc);
	}
}
