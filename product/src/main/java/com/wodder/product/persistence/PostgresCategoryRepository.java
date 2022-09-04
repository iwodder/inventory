package com.wodder.product.persistence;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import com.wodder.product.domain.model.category.CategoryRepository;
import com.zaxxer.hikari.HikariDataSource;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresCategoryRepository extends JdbcRepository
    implements CategoryRepository {

  private static final Logger LOG = LoggerFactory.getLogger(PostgresCategoryRepository.class);
  private static final String INSERT_CATEGORY_SQL =
      "INSERT INTO product.categories(uuid, name) VALUES (?,?);";
  private static final String DELETE_CATEGORY_SQL =
      "DELETE FROM product.categories WHERE uuid=?;";
  private static final String SELECT_SINGLE_CATEGORY =
      "SELECT * FROM product.categories WHERE uuid = ?;";
  private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM product.categories;";

  private static final ObjectMapper<Category> CATEGORY_OBJECT_MAPPER = rs -> new Category(
      CategoryId.categoryIdOf(rs.getString("uuid")),
      rs.getString("name")
  );

  PostgresCategoryRepository(HikariDataSource ds) {
    super(ds);
  }

  @Override
  public Optional<Category> loadById(CategoryId id) {
    List<Category> results = query(
        SELECT_SINGLE_CATEGORY, s -> {
          s.setString(1, id.getValue());
          return s;
        },
        CATEGORY_OBJECT_MAPPER);
    if (results.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(results.get(0));
    }
  }

  @Override
  public Optional<Category> loadByItem(Category item) {
    return loadById(item.getId());
  }

  @Override
  public Optional<Category> updateItem(Category item) {
    return Optional.empty();
  }

  @Override
  public List<Category> loadAllItems() {
    return query(SELECT_ALL_CATEGORIES, null, CATEGORY_OBJECT_MAPPER);
  }

  @Override
  public Category createItem(Category item) {
    try {
      List<Integer> ids = create(INSERT_CATEGORY_SQL, s -> {
        s.setString(1, item.getId().getValue());
        s.setString(2, item.getName());
        return s;
      });

      if (!ids.isEmpty()) {
        Field databaseId = item.getClass().getSuperclass().getDeclaredField("databaseId");
        databaseId.setAccessible(true);
        databaseId.set(item, ids.get(0).longValue());
      }
    } catch (Exception e) {
      LOG.error("Exception occurred during database insert", e);
    }
    return item;
  }

  @Override
  public boolean deleteItem(CategoryId id) {
    return 1 == update(DELETE_CATEGORY_SQL, s -> {
      s.setString(1, id.getValue());
      return s;
    });
  }

  @Override
  public boolean deleteItem(Category item) {
    return deleteItem(item.getId());
  }

  //openConnection()
  //createStatement()
  //potentiallyAddParams
  //execute
  //
}
