package com.wodder.inventory.apapters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wodder.inventory.application.inventory.ItemService;
import com.wodder.inventory.application.inventory.ItemServiceImpl;
import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.persistence.TestPersistenceFactory;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Demonstrates unit testing JAX-RS/Jersey REST APIS using HK2
// HK2 is manually configured in this example
class InventoryRestAdapterTestTwo extends JerseyTest {

  @Test
  @DisplayName("Unrecognized item id returns a 404")
  void itemNotFound() {
    Response r = target("inventory/item12345").request().get();

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Recognized item id returns a 200 with json body")
  void itemFound() {
    Response r = target("inventory/item123").request(MediaType.APPLICATION_JSON).get();

    assertEquals(200, r.getStatus());
    assertEquals(
        "{\"id\":\"item123\",\"name\":\"2% Milk\",\"location\":\"Refrigerator\"}",
        r.readEntity(String.class));
  }

  @Test
  @DisplayName("Creating an item returns the id")
  void createItem() {
    Response r = target("inventory/item").request().post(Entity.text("TEST"));

    assertEquals(200, r.getStatus());
    assertEquals("{\"id\":\"item123\"}", r.readEntity(String.class));
  }

  @Override
  protected Application configure() {
    return new ResourceConfig(ItemRestAdapter.class).register(new TestBinder());
  }

  public static class TestBinder extends AbstractBinder {

    @Override
    protected void configure() {
      bind(new ItemServiceImpl(TestPersistenceFactory.getPopulated().getRepository(Item.class)))
          .to(ItemService.class);
    }
  }
}
