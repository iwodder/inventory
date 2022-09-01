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
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostgresProductRepository extends JdbcRepository implements ProductRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(PostgresProductRepository.class);
  private static final String INSERT_PRODUCT = "INSERT INTO product.products"
      + "(uuid, \"extId\", name, \"catId\", units, \"itemsPerCase\", "
      + "\"unitPrice\", \"casePrice\", qty, active)"
      + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

  private static final String UPDATE_PRODUCT = "UPDATE product.products SET"
      + " name=?, \"extId\"=?, \"catId\"=?, units=?, \"itemsPerCase\"=?, \"unitPrice\"=?,"
      + " \"casePrice\"=?, qty=?, active=? WHERE uuid = ?;";

  private static final String SELECT_ACTIVE_PRODUCTS = 
      "SELECT p.uuid AS pid, p.\"extId\", p.\"name\" as pname, units, \"itemsPerCase\", "
      + "\"unitPrice\", \"casePrice\", qty, active, c.uuid AS cid, c.name AS cname "
      + "FROM product.products p JOIN product.categories c ON "
      + "p.\"catId\" = c.uuid WHERE p.active=true;";

  private static final String SELECT_BY_EXT_ID =
      "SELECT p.uuid AS pid, p.\"extId\", p.\"name\" as pname, units, \"itemsPerCase\", "
      + "\"unitPrice\", \"casePrice\", qty, active, c.uuid AS cid, c.name AS cname "
      + "FROM product.products p JOIN product.categories c ON "
      + "p.\"catId\" = c.uuid WHERE p.\"extId\" = ?;";

  private static final String SELECT_BY_ID =
      "SELECT p.uuid AS pid, p.\"extId\", p.\"name\" as pname, units, \"itemsPerCase\", "
          + "\"unitPrice\", \"casePrice\", qty, active, c.uuid AS cid, c.name AS cname "
          + "FROM product.products p JOIN product.categories c ON "
          + "p.\"catId\" = c.uuid WHERE p.uuid = ?;";

  private static final String SELECT_ALL =
      "SELECT p.uuid AS pid, p.\"extId\", p.\"name\" as pname, units, \"itemsPerCase\", "
          + "\"unitPrice\", \"casePrice\", qty, active, c.uuid AS cid, c.name AS cname "
          + "FROM product.products p JOIN product.categories c ON "
          + "p.\"catId\" = c.uuid;";

  private static final String DELETE_PRODUCT = "DELETE FROM product.products WHERE uuid = ?;";
  public static final ObjectMapper<Product> PRODUCT_MAPPER = rs ->
      Product.builder(
              ProductId.productIdOf(rs.getString("pid")),
              ProductName.of(rs.getString("pname")))
          .withCategory(
              Category.of(
                  CategoryId.categoryIdOf(
                      rs.getString("cid")), rs.getString("cname")))
          .withExternalId(ExternalId.of(rs.getString("extId")))
          .withUnitsOfMeasurement(UnitOfMeasurement.of(rs.getString("units")))
          .withCasePrice(Price.of(rs.getString("casePrice")))
          .withUnitPrice(Price.of(rs.getString("unitPrice")))
          .withStockedCount(Quantity.of(rs.getString("qty")))
          .withCasePack(rs.getString("itemsPerCase"))
          .isActive(rs.getBoolean("active"))
          .build();

  PostgresProductRepository(HikariDataSource ds) {
    super(ds);
  }

  @Override
  public List<Product> loadActiveItems() {
    return query(SELECT_ACTIVE_PRODUCTS, null, PRODUCT_MAPPER);
  }

  @Override
  public Optional<Product> loadByExternalId(ExternalId id) {
    List<Product> products = query(SELECT_BY_EXT_ID,
        s -> {
          s.setString(1, id.getValue());
          return s;
        },
        PRODUCT_MAPPER);

    return mapToOptional(products);
  }

  @Override
  public Optional<Product> loadById(ProductId id) {
    List<Product> products = query(SELECT_BY_ID,
        s -> {
          s.setString(1, id.getValue());
          return s;
        }, PRODUCT_MAPPER);

    return mapToOptional(products);
  }

  private static Optional<Product> mapToOptional(List<Product> products) {
    if (products.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(products.get(0));
    }
  }

  @Override
  public Optional<Product> loadByItem(Product item) {
    return loadById(item.getId());
  }

  @Override
  public Optional<Product> updateItem(Product item) {
    int count = update(UPDATE_PRODUCT, s -> {
      s.setString(1, item.getName().getValue());
      s.setString(2, item.getExternalId().getValue());
      s.setString(3, item.getCategory().getId().getValue());
      s.setString(4, item.getUnits().getUnit());
      s.setString(5, String.valueOf(item.getCasePack().getValue()));
      s.setString(6, item.getUnitPrice().stringValue());
      s.setString(7, item.getCasePrice().stringValue());
      s.setString(8, item.getQtyOnHand().getValue());
      s.setBoolean(9, item.isActive());
      s.setString(10, item.getId().getValue());
      return s;
    });
    return (count == 1) ? Optional.of(item) : Optional.empty();
  }

  @Override
  public List<Product> loadAllItems() {
    return query(SELECT_ALL, null, PRODUCT_MAPPER);
  }

  @Override
  public Product createItem(Product item) {
    try {
      List<Integer> ids = create(INSERT_PRODUCT, s -> {
        s.setString(1, item.getId().getValue());
        s.setString(2,
            item.getExternalId() == null ? null : item.getExternalId().getValue());
        s.setString(3,
            item.getName() == null ? null : item.getName().getValue());
        s.setString(4,
            item.getCategory() == null ? null : item.getCategory().getId().getValue());
        s.setString(5,
            item.getUnits() == null ? null : item.getUnits().getUnit());
        s.setString(6,
            item.getCasePack() == null ? null : String.valueOf(item.getCasePack().getValue()));
        s.setString(7,
            item.getUnitPrice() == null ? null : item.getUnitPrice().stringValue());
        s.setString(8,
            item.getCasePrice() == null ? null : item.getCasePrice().stringValue());
        s.setString(9,
            item.getQtyOnHand() == null ? null : item.getQtyOnHand().getValue());
        s.setBoolean(10,
            item.isActive());
        return s;
      });

      if (!ids.isEmpty()) {
        Field databaseId = item.getClass().getSuperclass().getDeclaredField("databaseId");
        databaseId.setAccessible(true);
        databaseId.set(item, ids.get(0).longValue());
      }
    } catch (Exception e) {
      LOGGER.error("Exception during database insert", e);
    }
    return item;
  }

  @Override
  public boolean deleteItem(ProductId id) {
    int cnt = update(DELETE_PRODUCT, s -> {
      s.setString(1, id.getValue());
      return s;
    });
    return cnt == 1;
  }

  @Override
  public boolean deleteItem(Product item) {
    return deleteItem(item.getId());
  }
}
