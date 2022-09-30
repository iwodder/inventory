package com.wodder.product.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wodder.product.application.CreateProductCommand;
import com.wodder.product.application.FakeProductEvent;
import com.wodder.product.application.ProductService;
import com.wodder.product.application.ProductServiceImpl;
import com.wodder.product.application.UpdateCategoryCommand;
import com.wodder.product.application.UpdateProductNameCommand;
import com.wodder.product.application.UpdateProductPriceCommand;
import com.wodder.product.application.UpdateUnitsCommand;
import com.wodder.product.domain.model.PersistenceFactory;
import com.wodder.product.dto.ProductDto;
import com.wodder.product.persistence.TestPersistenceFactory;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

@EnabledIfSystemProperty(named = "CDI", matches = "HK2")
class ProductRestAdapterTest extends JerseyTest {

  private static final ObjectMapper obj = new ObjectMapper();


  @Test
  @DisplayName("Accessing an non-existent product by id should return 404")
  void load_id_not_found() {
    Response r = target("product/p86910")
        .request().get();

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Accessing an existing product by id should return 200")
  void load_id() {
    Response r = target("product/p123")
        .request().get();

    assertEquals(200, r.getStatus());
  }

  @Test
  @DisplayName("Returned product should be in JSON")
  void load_json() throws JsonProcessingException {
    Response r = target("product/p123")
        .request().get();

    assertEquals(MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
    JsonNode actual = r.readEntity(JsonNode.class);

    JsonNode expected = obj.readValue(
        "{\"id\":\"p123\",\"name\":\"2% Milk\",\"category\":\"DAIRY\","
            + "\"units\":\"Gallons\",\"itemPrice\":\"2.98\",\"casePrice\":null,"
            + "\"active\":false,\"unitsPerCase\":0,\"externalId\":\"item1\","
            + "\"qtyOnHand\":\"0\"}", JsonNode.class);

    assertEquals(expected, actual, "Returned value didn't match.");
  }

  @Test
  @DisplayName("Creating a product should return a 201 with product id")
  void create_product()  {
    CreateProductCommand cmd = new CreateProductCommand();
    cmd.setCategoryId("c1");
    cmd.setName("Cheddar Cheese");
    cmd.setUnitMeasurement("ounces");
    cmd.setUnitPrice("1.90");

    try (Response r = target("product")
        .request()
        .post(Entity.json(cmd))) {

      assertEquals(200, r.getStatus());
      JsonNode result = r.readEntity(JsonNode.class);
      assertNotNull(result.get("id"));
    }
  }

  @Test
  @DisplayName("A created product should exist in the application")
  void created_product_is_found()  {
    CreateProductCommand cmd = new CreateProductCommand();
    cmd.setCategoryId("c1");
    cmd.setName("Cheddar Cheese");
    cmd.setUnitMeasurement("ounces");
    cmd.setUnitPrice("1.90");

    JsonNode id = target("product")
        .request()
        .post(Entity.json(cmd), JsonNode.class);

    Response get = target("product/"+id.get("id").textValue())
          .request(MediaType.APPLICATION_JSON)
          .get();
      assertEquals(200, get.getStatus(),
          () -> "Could find product with id=" + id.get("id").textValue());
  }

  @Test
  @DisplayName("Successfully deleting a product returns a 200")
  void delete_product()  {

    Response r = target("product/p123")
        .request()
        .delete();

    assertEquals(200, r.getStatus());
  }

  @Test
  @DisplayName("Deleting a non-existent product returns a 404")
  void delete_nonexistent_product()  {

    Response r = target("product/p8675309")
        .request()
        .delete();

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Updating a product category returns a 201")
  void update_category() {
    UpdateCategoryCommand cmd = new UpdateCategoryCommand();
    cmd.setCategory("frozen");
    Response r = target("product/p123/category")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(201, r.getStatus());
    ProductDto dto = r.readEntity(ProductDto.class);
    assertTrue("frozen".equalsIgnoreCase(dto.getCategory()));
  }

  @Test
  @DisplayName("Updating the category for a nonexistent product returns 404")
  void update_category_not_found() {
    UpdateCategoryCommand cmd = new UpdateCategoryCommand();
    cmd.setCategory("frozen");
    Response r = target("product/p8675/category")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Updating a product name returns a 201")
  void update_name() {
    UpdateProductNameCommand cmd = new UpdateProductNameCommand();
    cmd.setName("newName");
    Response r = target("product/p123/name")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(201, r.getStatus());
    ProductDto dto = r.readEntity(ProductDto.class);
    assertTrue("newName".equalsIgnoreCase(dto.getName()));
  }

  @Test
  @DisplayName("Updating the name for a nonexistent product returns 404")
  void update_name_not_found() {
    UpdateProductNameCommand cmd = new UpdateProductNameCommand();
    cmd.setName("newName");
    Response r = target("product/p8675/name")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Updating a product's unit returns a 201")
  void update_units() {
    UpdateUnitsCommand cmd = new UpdateUnitsCommand();
    cmd.setUnits("quarts");
    Response r = target("product/p123/units")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(201, r.getStatus());
    ProductDto dto = r.readEntity(ProductDto.class);
    assertTrue("quarts".equalsIgnoreCase(dto.getUnits()));
  }

  @Test
  @DisplayName("Updating the units for a nonexistent product returns 404")
  void update_units_not_found() {
    UpdateUnitsCommand cmd = new UpdateUnitsCommand();
    cmd.setUnits("quarts");
    Response r = target("product/p8675/units")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Updating a product's price returns a 201")
  void update_price() {
    UpdateProductPriceCommand cmd = new UpdateProductPriceCommand();
    cmd.setItemPrice("10.99");
    cmd.setCasePrice("13.49");
    Response r = target("product/p123/price")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(201, r.getStatus());
    ProductDto dto = r.readEntity(ProductDto.class);
    assertEquals("10.99", dto.getItemPrice());
    assertEquals("13.49", dto.getCasePrice());
  }

  @Test
  @DisplayName("Updating the price for a nonexistent product returns 404")
  void update_price_not_found() {
    UpdateProductPriceCommand cmd = new UpdateProductPriceCommand();
    cmd.setItemPrice("10.99");
    cmd.setCasePrice("13.49");
    Response r = target("product/p8675/price")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Should be able to update only the item price")
  void update_item_price() {
    UpdateProductPriceCommand cmd = new UpdateProductPriceCommand();
    cmd.setItemPrice("10.99");
    Response r = target("product/p123/price")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(201, r.getStatus());
    ProductDto dto = r.readEntity(ProductDto.class);
    assertEquals("10.99", dto.getItemPrice());
  }

  @Test
  @DisplayName("Should be able to update only the case price")
  void update_case_price() {
    UpdateProductPriceCommand cmd = new UpdateProductPriceCommand();
    cmd.setCasePrice("13.99");
    Response r = target("product/p123/price")
        .request(MediaType.APPLICATION_JSON)
        .method("PATCH", Entity.json(cmd));

    assertEquals(201, r.getStatus());
    ProductDto dto = r.readEntity(ProductDto.class);
    assertEquals("13.99", dto.getCasePrice());
  }

  @Test
  @DisplayName("Should be able to load all products")
  void load_products() {
    Response r = target("product")
        .request(MediaType.APPLICATION_JSON)
        .get();

    assertEquals(200, r.getStatus());
    assertEquals(MediaType.APPLICATION_JSON_TYPE, r.getMediaType());
    List<ProductDto> products = r.readEntity(new GenericType<>() {});

    assertFalse(products.isEmpty());
  }

  @Override
  protected Application configure() {
    return new ResourceConfig(ProductRestAdapter.class)
        .register(new TestBinder());
  }

  public static class TestBinder extends AbstractBinder {

    @Override
    protected void configure() {
      PersistenceFactory tpf = TestPersistenceFactory.getPopulated();
      bind(new ProductServiceImpl(
          tpf.getProductRepository(),
          tpf.getCategoryRepository(),
          tpf.getShipmentRepository(),
          new FakeProductEvent()
      )).to(ProductService.class);
    }
  }
}
