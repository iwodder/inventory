package com.wodder.inventory.apapters;

import com.wodder.inventory.application.inventory.AddItemCommand;
import com.wodder.inventory.application.inventory.ItemService;
import com.wodder.inventory.dto.ItemDto;
import java.util.Optional;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("inventory")
public class InventoryRestAdapter {
  private static final Logger LOGGER = LoggerFactory.getLogger(InventoryRestAdapter.class);
  private final ItemService service;

  @Inject
  public InventoryRestAdapter(ItemService service) {
    this.service = service;
  }

  @GET
  @Path("{itemId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getItem(@PathParam("itemId") String id) {
    LOGGER.info("getItem >> Received id {}", id);
    Optional<ItemDto> dto = service.loadItem(id);
    if (dto.isPresent()) {
      return Response.ok(dto.get()).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @POST
  @Path("item")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createItem(AddItemCommand command) {
    LOGGER.info("createItem >> Received command {}", command);
    String id = service.addItem(command);
    JsonObject obj = Json.createObjectBuilder().add("id", id).build();
    return Response.ok(obj.toString()).build();
  }

  @DELETE
  @Path("{itemId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteItem(@PathParam("itemId") String id) {
    LOGGER.info("deleteItem >> Received id {}", id);
    service.deleteItem(id);
    return Response.ok(
        Json.createObjectBuilder().add("result", "success").build().toString()
    ).build();
  }
}
