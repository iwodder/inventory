package com.wodder.inventory.apapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.JsonNode;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InventoryRestAdapterTest extends JerseyTest {

  @Override
  protected ResourceConfig configure() {
    return new ResourceConfig(InventoryRestAdapter.class);
  }

  @Test
  @DisplayName("Should be able to create a new inventory")
  void newInventory() {
    Response r = target("inventory")
        .request(MediaType.APPLICATION_JSON)
        .post(null);

    assertEquals(Status.OK.getStatusCode(), r.getStatus(), "Expected OK (200) status code.");
    JsonNode entity = r.readEntity(JsonNode.class);
    JsonNode dateField = entity.get("inventoryDate");
    assertNotNull(dateField, "Expected JSON response to have an inventoryDate field.");
    assertFalse(dateField.textValue().isBlank(), "inventoryDate was empty");
  }
}
