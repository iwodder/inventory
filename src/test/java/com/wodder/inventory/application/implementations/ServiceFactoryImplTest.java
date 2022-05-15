package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceFactoryImplTest {

	ServiceFactory svc;
	@BeforeEach
	void setup() {
		svc = new ServiceFactoryImpl(TestPersistenceFactory.getUnpopulated());
	}

	@Test
	@DisplayName("Can access service class through interface")
	void service_interface() {
		assertNotNull(svc.getService(ProductService.class));
	}

	@Test
	@DisplayName("Can load the Inventory Service")
	void inventory_service() {
		assertNotNull(svc.getService(InventoryService.class));
	}
}
