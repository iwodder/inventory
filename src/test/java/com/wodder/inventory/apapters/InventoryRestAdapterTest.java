package com.wodder.inventory.apapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Demonstrates unit testing JAX-RS/Jersey REST APIS using Weld
// Weld-SE
class InventoryRestAdapterTest extends JerseyTest {

  @Override
  protected ResourceConfig configure() {
    return new ResourceConfig(InventoryRestAdapter.class);
  }

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
    assertEquals(MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
    assertEquals(
        "{\"id\":\"item123\",\"productId\":\"p123\",\"name\":\"2% Milk\",\"location\":\"Refrigerator\"}",
        r.readEntity(String.class));
  }

  @Test
  @DisplayName("Creating an item returns the id")
  void createItem() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode input = mapper.createObjectNode();
    input.put("productId", "123");
    input.put("name", "Chocolate Milk");
    input.put("location", "Refrigerator");
    input.put("measurementUnit", "Gallons");

    Response r = target("inventory/item")
        .request()
        .post(Entity.json(input.toString()));

    assertEquals(200, r.getStatus());
    JsonNode response = r.readEntity(JsonNode.class);

    assertFalse(response.get("id").toString().isBlank());
  }

  @Test
  @DisplayName("Deleting an item returns 200 and success")
  void deleteItem() {
    Response r = target("inventory/item123").request(MediaType.APPLICATION_JSON).delete();

    assertEquals(200, r.getStatus());
    assertEquals("{\"result\":\"success\"}", r.readEntity(String.class));
  }
}
