package com.wodder.product.adapters;

import com.wodder.product.application.CategoryService;
import com.wodder.product.application.CreateCategoryCommand;
import com.wodder.product.dto.CategoryDto;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("category")
public class CategoryRestAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestAdapter.class);
  private final CategoryService svc;

  @Inject
  CategoryRestAdapter(CategoryService svc) {
    this.svc = svc;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response loadAllCategories() {
    LOGGER.info(">> loadAllCategories()");
    return Response.ok(svc.loadCategories()).build();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response loadCategory(@PathParam("id") String id) {
    LOGGER.info(">> loadCategory(id={})", id);
    Optional<CategoryDto> opt = svc.loadCategory(id);
    if (opt.isPresent()) {
      return Response.ok(opt.get()).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createCategory(CreateCategoryCommand cmd) {
    LOGGER.info(">> createCategory(cmd={})", cmd);
    return Response.ok(svc.createCategory(cmd.getName())).build();
  }
}
