package com.wodder.product.persistence;

import com.wodder.product.domain.model.Repository;
import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import com.zaxxer.hikari.HikariDataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresCategoryRepository implements Repository<Category, CategoryId> {
  private static final Logger LOG = LoggerFactory.getLogger(PostgresCategoryRepository.class);
  private final HikariDataSource dataSource;

  PostgresCategoryRepository(HikariDataSource ds) {
    this.dataSource = ds;
  }

  @Override
  public Optional<Category> loadById(CategoryId id) {
    Category result;
    try (Connection c = dataSource.getConnection();
        PreparedStatement ps =
            c.prepareStatement("SELECT * FROM product.categories WHERE uuid = ?");) {
      ps.setString(1, id.getValue());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        String uuid = rs.getString("uuid");
        String name = rs.getString("name");
        result = new Category(CategoryId.categoryIdOf(uuid), name);
        return Optional.of(result);
      }
    } catch (Exception e) {
      LOG.error("Exception occurred during database retrieval", e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Category> loadByItem(Category item) {
    try (Connection c = dataSource.getConnection();
        PreparedStatement ps =
            c.prepareStatement("SELECT * FROM product.categories WHERE uuid = ? AND name = ?");) {
      ps.setString(1, item.getId().getValue());
      ps.setString(2, item.getName());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        String uuid = rs.getString("uuid");
        String name = rs.getString("name");
        return Optional.of(new Category(CategoryId.categoryIdOf(uuid), name));
      }
    } catch (Exception e) {
      LOG.error("Exception occurred during database retrieval", e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Category> updateItem(Category item) {
    return Optional.empty();
  }

  @Override
  public List<Category> loadAllItems() {
    List<Category> result = new ArrayList<>();
    try (Connection c = dataSource.getConnection();
        PreparedStatement ps =
            c.prepareStatement("SELECT * FROM product.categories");
    ) {
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String uuid = rs.getString("uuid");
        String name = rs.getString("name");
        result.add(new Category(CategoryId.categoryIdOf(uuid), name));
      }
    } catch (Exception e) {
      LOG.error("Exception occurred during database retrieval", e);
    }
    return result;
  }

  @Override
  public Category createItem(Category item) {
    try (Connection c = dataSource.getConnection();
        PreparedStatement ps =
            c.prepareStatement(
                "INSERT INTO product.categories(uuid, name) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS);
    ) {
      ps.setString(1, item.getId().getValue());
      ps.setString(2, item.getName());
      ps.executeUpdate();

      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        int id = rs.getInt(1);
        Field databaseId = item.getClass().getSuperclass().getDeclaredField("databaseId");
        databaseId.setAccessible(true);
        databaseId.set(item, (long) id);
      }
    } catch (Exception e) {
      LOG.error("Exception occurred during database insert", e);
    }
    return item;
  }

  @Override
  public boolean deleteItem(CategoryId id) {
    try (Connection c = dataSource.getConnection();
        PreparedStatement ps =
            c.prepareStatement("DELETE FROM product.categories WHERE uuid=?");
    ) {
      ps.setString(1, id.getValue());
      ps.executeUpdate();
    } catch (Exception e) {
      LOG.error("Exception occurred during database delete", e);
      return false;
    }
    return true;
  }

  @Override
  public boolean deleteItem(Category item) {
    try (Connection c = dataSource.getConnection();
        PreparedStatement ps =
            c.prepareStatement("DELETE FROM product.categories WHERE uuid=? AND name=?");
    ) {
      ps.setString(1, item.getId().getValue());
      ps.setString(2, item.getName());
      ps.executeUpdate();
    } catch (Exception e) {
      LOG.error("Exception occurred during database delete", e);
      return false;
    }
    return true;
  }
}
