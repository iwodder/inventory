package com.wodder.inventory.apapters;

import com.wodder.inventory.application.inventory.InventoryService;
import com.wodder.inventory.domain.model.inventory.Inventory;
import com.wodder.inventory.dto.InventoryDto;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("inventory")
public class InventoryRestAdapter {
  private static final Logger LOGGER = LoggerFactory.getLogger(InventoryRestAdapter.class);
  private final InventoryService service;

  @Inject
  public InventoryRestAdapter(InventoryService service) {
    this.service = service;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response newInventory() {
    LOGGER.info("newInventory >> Creating new inventory");
    Inventory i = service.createInventory();
    InventoryDto dto = i.toModel();
    LOGGER.info("Inventory Json {}", dto);
    return Response.ok(dto).build();
  }
}
