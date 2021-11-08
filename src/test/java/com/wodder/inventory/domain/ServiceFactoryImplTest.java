package com.wodder.inventory.domain;

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
	@DisplayName("Can provide the ItemStorageService")
	void item_service() {
		ItemStorage itemStorage = impl.getItemStorage();
		assertNotNull(itemStorage);
	}

	@Test
	@DisplayName("Can access service class through interface")
	void service_interface() {
		ItemStorage itemStorage = impl.getService(ItemStorage.class);
		assertNotNull(itemStorage);
	}
}
