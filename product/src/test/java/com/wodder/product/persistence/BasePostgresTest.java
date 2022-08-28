package com.wodder.product.persistence;

import static org.junit.jupiter.api.Assertions.fail;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.Statement;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BasePostgresTest {
  static HikariDataSource ds;

  @BeforeAll
  public static void init() {
    ds = new HikariDataSource(new HikariConfig("/datasource-test.properties"));

    try (Connection c = ds.getConnection();
        Statement s = c.createStatement()
    ) {
      s.execute(
          "CREATE TABLE IF NOT EXISTS product.categories "
              + "(id serial NOT NULL,"
              + "uuid character varying(36) NOT NULL,"
              + "name character varying(256) NOT NULL,"
              + "PRIMARY KEY (id),"
              + "CONSTRAINT \"c_uuid\" UNIQUE (uuid));"
              + "ALTER TABLE IF EXISTS product.categories OWNER to productuser;");
      
      s.execute("CREATE TABLE IF NOT EXISTS product.products ("
          + "id serial NOT NULL,"
          + "uuid character varying(36) NOT NULL,"
          + "\"extId\" character varying(256),"
          + "name character varying(256),"
          + "\"catId\" character varying(36),"
          + "units character varying(256),"
          + "\"itemsPerCase\" character varying(6),"
          + "\"unitPrice\" character varying(14),"
          + "\"casePrice\" character varying(14),"
          + "qty character varying(14),"
          + "active boolean NOT NULL DEFAULT true,"
          + "PRIMARY KEY (id),"
          + "CONSTRAINT \"p_uuid\" UNIQUE (uuid),"
          + "CONSTRAINT \"fk_catId\" FOREIGN KEY (\"catId\")"
          + "REFERENCES product.categories (uuid) MATCH SIMPLE"
          + " ON UPDATE NO ACTION"
          + " ON DELETE NO ACTION);"
          + "ALTER TABLE IF EXISTS product.products OWNER to productuser;");

    } catch (Exception e) {
      fail("Failed to setup test infrastructure.", e);
    }
  }

  @AfterAll
  static void cleanup() {
    try (Connection c = ds.getConnection();
        Statement s = c.createStatement();
    ) {
      s.execute("DROP TABLE product.products");
      s.execute("DROP TABLE product.categories");
      ds.close();
    } catch (Exception e) {
      fail("Failed to teardown test infrastructure.", e);
    }
  }
}
