package com.wodder.inventory.apapters;

import com.wodder.inventory.application.inventory.InventoryService;
import com.wodder.inventory.domain.model.inventory.Inventory;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.WILDCARD)
  public Response newInventory() {
    LOGGER.info("Creating new inventory.");
    Inventory i = service.createInventory();
    return Response.ok(i.toModel()).build();
  }
}
