package com.wodder.inventory.apapters;

import com.wodder.inventory.application.inventory.ItemService;
import com.wodder.inventory.dto.ItemDto;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("inventory")
public class InventoryRestAdapter {

  private final ItemService service;

  @Inject
  public InventoryRestAdapter(ItemService service) {
    this.service = service;
  }

  @GET
  @Path("{itemId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getItem(@PathParam("itemId") String id) {
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
  public Response createItem(ItemDto itemDto) {
    return Response.serverError().build();
  }
}
