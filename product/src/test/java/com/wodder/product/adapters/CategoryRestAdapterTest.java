package com.wodder.product.adapters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.wodder.product.application.CategoryService;
import com.wodder.product.application.CategoryServiceImpl;
import com.wodder.product.application.CreateCategoryCommand;
import com.wodder.product.domain.model.PersistenceFactory;
import com.wodder.product.dto.CategoryDto;
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
class CategoryRestAdapterTest extends JerseyTest {

  @Test
  @DisplayName("Should be able to load all categories")
  void loadAll() {
    Response r = target("category")
        .request(MediaType.APPLICATION_JSON)
        .get();

    assertEquals(200, r.getStatus());
    List<CategoryDto> result = r.readEntity(new GenericType<List<CategoryDto>>() {});
    assertEquals(5, result.size());
  }

  @Test
  @DisplayName("Should be able to load a single category")
  void loadOne() {
    Response r = target("category/c2")
        .request(MediaType.APPLICATION_JSON)
        .get();

    assertEquals(200, r.getStatus());
    CategoryDto result = r.readEntity(CategoryDto.class);
    assertEquals("DAIRY", result.getName());
  }

  @Test
  @DisplayName("Trying to access a nonexistent category should return 404")
  void loadNonExistent() {
    Response r = target("category/c123")
        .request(MediaType.APPLICATION_JSON)
        .get();

    assertEquals(404, r.getStatus());
  }

  @Test
  @DisplayName("Should be able to create a new category")
  void newCategory() {
    CreateCategoryCommand cmd = new CreateCategoryCommand();
    cmd.setName("Fantasy Food");

    Response r = target("category")
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.json(cmd));

    assertEquals(200, r.getStatus());
    CategoryDto dto = r.readEntity(CategoryDto.class);
    assertEquals("FANTASY FOOD", dto.getName());
    assertNotNull(dto.getId());
  }

  @Override
  protected Application configure() {
    return new ResourceConfig(CategoryRestAdapter.class)
        .register(new CategoryRestAdapterTest.TestBinder());
  }

  public static class TestBinder extends AbstractBinder {

    @Override
    protected void configure() {
      PersistenceFactory tpf = TestPersistenceFactory.getPopulated();
      bind(new CategoryServiceImpl(
          tpf.getCategoryRepository()
      )).to(CategoryService.class);
    }
  }
}
