package com.wodder.product.persistence;

import com.wodder.product.domain.model.category.Category;
import com.wodder.product.domain.model.category.CategoryId;
import com.wodder.product.domain.model.product.ExternalId;
import com.wodder.product.domain.model.product.Price;
import com.wodder.product.domain.model.product.Product;
import com.wodder.product.domain.model.product.ProductId;
import com.wodder.product.domain.model.product.ProductName;
import com.wodder.product.domain.model.product.ProductRepository;
import com.wodder.product.domain.model.product.Quantity;
import com.wodder.product.domain.model.product.UnitOfMeasurement;
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

public class PostgresProductRepository implements ProductRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(PostgresProductRepository.class);
  private static final String INSERT_SQL = "INSERT INTO product.products"
      + "(uuid, \"extId\", name, \"catId\", units, \"itemsPerCase\", "
      + "\"unitPrice\", \"casePrice\", qty, active)"
      + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

  private static final String UPDATE_SQL = "UPDATE product.products SET"
      + " name=?, \"extId\"=?, \"catId\"=?, units=?, \"itemsPerCase\"=?, \"unitPrice\"=?,"
      + " \"casePrice\"=?, qty=?, active=? WHERE uuid = ?";
  private final HikariDataSource ds;

  PostgresProductRepository(HikariDataSource ds) {
    this.ds = ds;
  }

  @Override
  public List<Product> loadActiveItems() {
    List<Product> result = new ArrayList<>();
    try (Connection c = ds.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "SELECT p.uuid AS pid, p.\"extId\", p.\"name\" as pname, units, \"itemsPerCase\", "
                + "\"unitPrice\", \"casePrice\", qty, active, c.uuid AS cid, c.name AS cname "
                + "FROM product.products p JOIN product.categories c ON "
                + "p.\"catId\" = c.uuid WHERE p.active=true");
    ) {
      if (ps.execute()) {
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
          result.add(
              Product.builder(
                      ProductId.productIdOf(rs.getString("pid")),
                      ProductName.of(rs.getString("pname")))
                  .withCategory(
                      Category.of(
                          CategoryId.categoryIdOf(rs.getString("cid")), rs.getString("cname")))
                  .withExternalId(ExternalId.of(rs.getString("extId")))
                  .withUnitsOfMeasurement(UnitOfMeasurement.of(rs.getString("units")))
                  .withCasePrice(Price.of(rs.getString("casePrice")))
                  .withUnitPrice(Price.of(rs.getString("unitPrice")))
                  .withStockedCount(Quantity.of(rs.getString("qty")))
                  .withCasePack(rs.getString("itemsPerCase"))
                  .isActive(rs.getBoolean("active"))
                  .build()
          );
        }
      }
    } catch (Exception e) {
      LOGGER.error("Unable to retrieve product", e);
    }
    return result;
  }

  @Override
  public Optional<Product> loadByExternalId(ExternalId id) {
    try (Connection c = ds.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "SELECT p.uuid AS pid, p.\"extId\", p.\"name\" as pname, units, \"itemsPerCase\", "
                + "\"unitPrice\", \"casePrice\", qty, active, c.uuid AS cid, c.name AS cname "
                + "FROM product.products p JOIN product.categories c ON "
                + "p.\"catId\" = c.uuid WHERE p.\"extId\" = ?");
    ) {
      ps.setString(1, id.getValue());
      ps.execute();

      ResultSet rs = ps.getResultSet();
      if (rs.next()) {
        return Optional.of(
            Product.builder(
                    ProductId.productIdOf(rs.getString("pid")),
                    ProductName.of(rs.getString("pname")))
                .withCategory(
                    Category.of(
                        CategoryId.categoryIdOf(rs.getString("cid")), rs.getString("cname")))
                .withExternalId(ExternalId.of(rs.getString("extId")))
                .withUnitsOfMeasurement(UnitOfMeasurement.of(rs.getString("units")))
                .withCasePrice(Price.of(rs.getString("casePrice")))
                .withUnitPrice(Price.of(rs.getString("unitPrice")))
                .withStockedCount(Quantity.of(rs.getString("qty")))
                .withCasePack(rs.getString("itemsPerCase"))
                .isActive(rs.getBoolean("active"))
                .build()
        );
      }
    } catch (Exception e) {
      LOGGER.error("Unable to retrieve product", e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Product> loadById(ProductId id) {
    try (Connection c = ds.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "SELECT p.uuid AS pid, p.\"extId\", p.\"name\" as pname, units, \"itemsPerCase\", "
                + "\"unitPrice\", \"casePrice\", qty, active, c.uuid AS cid, c.name AS cname "
                + "FROM product.products p JOIN product.categories c ON "
                + "p.\"catId\" = c.uuid WHERE p.uuid = ?");
    ) {
      ps.setString(1, id.getValue());
      ps.execute();

      ResultSet rs = ps.getResultSet();
      if (rs.next()) {
        return Optional.of(
            Product.builder(
                ProductId.productIdOf(rs.getString("pid")),
                    ProductName.of(rs.getString("pname")))
                .withCategory(
                    Category.of(
                        CategoryId.categoryIdOf(rs.getString("cid")), rs.getString("cname")))
                .withExternalId(ExternalId.of(rs.getString("extId")))
                .withUnitsOfMeasurement(UnitOfMeasurement.of(rs.getString("units")))
                .withCasePrice(Price.of(rs.getString("casePrice")))
                .withUnitPrice(Price.of(rs.getString("unitPrice")))
                .withStockedCount(Quantity.of(rs.getString("qty")))
                .withCasePack(rs.getString("itemsPerCase"))
                .isActive(rs.getBoolean("active"))
                .build()
        );
      }
    } catch (Exception e) {
      LOGGER.error("Unable to retrieve product", e);
    }
    return Optional.empty();
  }

  @Override
  public Optional<Product> loadByItem(Product item) {
    return loadById(item.getId());
  }

  @Override
  public Optional<Product> updateItem(Product item) {
    try (Connection c = ds.getConnection();
        PreparedStatement ps = c.prepareStatement(UPDATE_SQL);
    ) {
      ps.setString(1, item.getName().getValue());
      ps.setString(2, item.getExternalId().getValue());
      ps.setString(3, item.getCategory().getId().getValue());
      ps.setString(4, item.getUnits().getUnit());
      ps.setString(5, String.valueOf(item.getCasePack().getValue()));
      ps.setString(6, item.getUnitPrice().stringValue());
      ps.setString(7, item.getCasePrice().stringValue());
      ps.setString(8, item.getQtyOnHand().getValue());
      ps.setBoolean(9, item.isActive());
      ps.setString(10, item.getId().getValue());
      ps.executeUpdate();
    } catch (Exception e) {
      LOGGER.error("Unable to update product", e);
      return Optional.empty();
    }
    return Optional.of(item);
  }

  @Override
  public List<Product> loadAllItems() {
    List<Product> result = new ArrayList<>();
    try (Connection c = ds.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "SELECT p.uuid AS pid, p.\"extId\", p.\"name\" as pname, units, \"itemsPerCase\", "
                + "\"unitPrice\", \"casePrice\", qty, active, c.uuid AS cid, c.name AS cname "
                + "FROM product.products p JOIN product.categories c ON "
                + "p.\"catId\" = c.uuid");
    ) {
      if (ps.execute()) {
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
          result.add(
              Product.builder(
                      ProductId.productIdOf(rs.getString("pid")),
                      ProductName.of(rs.getString("pname")))
                  .withCategory(
                      Category.of(
                          CategoryId.categoryIdOf(rs.getString("cid")), rs.getString("cname")))
                  .withExternalId(ExternalId.of(rs.getString("extId")))
                  .withUnitsOfMeasurement(UnitOfMeasurement.of(rs.getString("units")))
                  .withCasePrice(Price.of(rs.getString("casePrice")))
                  .withUnitPrice(Price.of(rs.getString("unitPrice")))
                  .withStockedCount(Quantity.of(rs.getString("qty")))
                  .withCasePack(rs.getString("itemsPerCase"))
                  .isActive(rs.getBoolean("active"))
                  .build()
          );
        }
      }
    } catch (Exception e) {
      LOGGER.error("Unable to retrieve product", e);
    }
    return result;
  }

  @Override
  public Product createItem(Product item) {
    try (Connection c = ds.getConnection();
        PreparedStatement ps = c.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
    ) {
      ps.setString(1, item.getId().getValue());
      ps.setString(2, item.getExternalId() == null ? null : item.getExternalId().getValue());
      ps.setString(3, item.getName() == null ? null : item.getName().getValue());
      ps.setString(4, item.getCategory() == null ? null : item.getCategory().getId().getValue());
      ps.setString(5, item.getUnits() == null ? null : item.getUnits().getUnit());
      ps.setString(6, item.getCasePack() == null ? null : String.valueOf(item.getCasePack().getValue()));
      ps.setString(7, item.getUnitPrice() == null ? null : item.getUnitPrice().stringValue());
      ps.setString(8, item.getCasePrice() == null ? null : item.getCasePrice().stringValue());
      ps.setString(9, item.getQtyOnHand() == null ? null : item.getQtyOnHand().getValue());
      ps.setBoolean(10, item.isActive());

      ps.executeUpdate();
      ResultSet rs = ps.getGeneratedKeys();
      if (rs.next()) {
        int id = rs.getInt(1);
        Field databaseId = item.getClass().getSuperclass().getDeclaredField("databaseId");
        databaseId.setAccessible(true);
        databaseId.set(item, (long) id);
      }
    } catch (Exception e) {
      LOGGER.error("Unable to create product", e);
    }
    return item;
  }

  @Override
  public boolean deleteItem(ProductId id) {
    try (Connection c = ds.getConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM product.products WHERE uuid = ?");
    ) {
        ps.setString(1, id.getValue());
        ps.executeUpdate();
    } catch (Exception e) {
      LOGGER.error("Unable to delete product", e);
      return false;
    }
    return true;
  }

  @Override
  public boolean deleteItem(Product item) {
    return deleteItem(item.getId());
  }
}
