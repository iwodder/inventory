package com.wodder.product.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wodder.product.application.CreateProductCommand;
import com.wodder.product.application.ProductService;
import com.wodder.product.application.UpdateCategoryCommand;
import com.wodder.product.application.UpdateProductNameCommand;
import com.wodder.product.application.UpdateProductPriceCommand;
import com.wodder.product.application.UpdateUnitsCommand;
import com.wodder.product.dto.ProductDto;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("product")
public class ProductRestAdapter {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestAdapter.class);
  private final ProductService svc;

  @Inject
  ProductRestAdapter(ProductService svc) {
    this.svc = svc;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getProducts() {
    LOGGER.info(">> getProducts()");
    return Response.ok(svc.loadAllProducts()).build();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getProduct(@PathParam("id") String productId) {
    LOGGER.info(">> getProduct(productId={})", productId);
    Optional<ProductDto> opt = svc.loadProductById(productId);
    if (opt.isPresent()) {
      return Response.ok(opt.get(), MediaType.APPLICATION_JSON_TYPE).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response postProduct(CreateProductCommand cmd) {
    LOGGER.info(">> postProduct(cmd={})", cmd);
    String result = svc.createProduct(cmd);
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode node = mapper.createObjectNode();
    node.put("id", result);
    return Response.ok(node, MediaType.APPLICATION_JSON_TYPE).build();
  }

  @DELETE
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteProduct(@PathParam("id") String productId) {
    LOGGER.info(">> deleteProduct(productId={})", productId);
    if (svc.deleteProduct(productId)) {
      return Response.ok().build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @PATCH
  @Path("{id}/category")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateCategory(@PathParam("id") String productId, UpdateCategoryCommand cmd) {
    LOGGER.info(">> updateCategory(productId={}, cmd={})", productId, cmd);
    Optional<ProductDto> opt = svc.updateProductCategory(productId, cmd.getCategory());
    if (opt.isPresent()) {
      return Response.status(Status.CREATED).entity(opt.get()).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @PATCH
  @Path("{id}/name")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateName(@PathParam("id") String productId, UpdateProductNameCommand cmd) {
    LOGGER.info(">> updateName(productId={}, cmd={})", productId, cmd);
    Optional<ProductDto> opt = svc.updateProductName(productId, cmd.getName());
    if (opt.isPresent()) {
      return Response.status(Status.CREATED).entity(opt.get()).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @PATCH
  @Path("{id}/units")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateUnits(@PathParam("id") String productId, UpdateUnitsCommand cmd) {
    LOGGER.info(">> updateUnits(productId={}, cmd={})", productId, cmd);
    Optional<ProductDto> opt = svc.updateProductUnitOfMeasurement(productId, cmd.getUnits());
    if (opt.isPresent()) {
      return Response.status(Status.CREATED).entity(opt.get()).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @PATCH
  @Path("{id}/price")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePrice(@PathParam("id") String productId, UpdateProductPriceCommand cmd) {
    LOGGER.info(">> updatePrice(productId={}, cmd={})", productId, cmd);
    Optional<ProductDto> opt =
        svc.updateProductPrice(productId, cmd.getItemPrice(), cmd.getCasePrice());
    if (opt.isPresent()) {
      return Response.status(Status.CREATED).entity(opt.get()).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
}
