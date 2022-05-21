package com.wodder.inventory.application.implementations;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.wodder.inventory.application.InventoryService;
import com.wodder.inventory.application.ProductService;
import com.wodder.inventory.application.ServiceFactory;
import com.wodder.inventory.persistence.TestPersistenceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
