package com.wodder.inventory.apapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wodder.inventory.application.inventory.CopyItemCommand;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Demonstrates unit testing JAX-RS/Jersey REST APIS using Weld
// Weld-SE
class ItemRestAdapterTest extends JerseyTest {

  @Override
  protected ResourceConfig configure() {
    return new ResourceConfig(ItemRestAdapter.class);
  }

  @Test
  @DisplayName("Unrecognized item id returns a 404")
  void itemNotFound() {
    Response r = target("item/item12345").request().get();

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Recognized item id returns a 200 with json body")
  void itemFound() {
    Response r = target("item/item123").request(MediaType.APPLICATION_JSON).get();

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

    Response r = target("item/item")
        .request()
        .post(Entity.json(input.toString()));

    assertEquals(200, r.getStatus());
    JsonNode response = r.readEntity(JsonNode.class);

    assertFalse(response.get("id").toString().isBlank());
  }

  @Test
  @DisplayName("Deleting an item returns 200 and success")
  void deleteItem() {
    Response r = target("item/item123").request(MediaType.APPLICATION_JSON).delete();

    assertEquals(200, r.getStatus());
    assertEquals("{\"result\":\"success\"}", r.readEntity(String.class));
  }

  @Test
  @DisplayName("Moving an item returns 200 and an updated item json")
  void moveItem() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode input = mapper
        .createObjectNode()
        .put("location", "pantry");

    Response r = target("item/item123")
        .request(MediaType.APPLICATION_JSON)
        .put(Entity.json(input.toString()));

    JsonNode json = r.readEntity(JsonNode.class);
    assertEquals(200, r.getStatus());
    assertEquals("pantry", json.get("location").textValue());
  }

  @Test
  @DisplayName("Moving an unknown item returns a 404")
  void moveItemMissing() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode input = mapper
        .createObjectNode()
        .put("location", "pantry");

    Response r = target("item/item123456")
        .request(MediaType.APPLICATION_JSON)
        .put(Entity.json(input.toString()));

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Copying item returns a 200 and json response")
  void copyItem() {
    CopyItemCommand c = new CopyItemCommand();
    c.setItemId("item123");
    c.setLocation("freezer");

    Response r = target("item/copy")
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.json(c));

    assertEquals(200, r.getStatus());
    JsonNode node = r.readEntity(JsonNode.class);
    assertFalse(node.get("id").textValue().isBlank());
  }
}
