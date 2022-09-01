package com.wodder.product.persistence;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JdbcRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcRepository.class);
  protected final HikariDataSource dataSource;

  JdbcRepository(HikariDataSource ds) {
    this.dataSource = ds;
  }

  List<Integer> create(String sql, ParameterMapper mapper) {
    List<Integer> ids = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
      mapper.map(stmt).executeUpdate();

      ResultSet rs = stmt.getGeneratedKeys();
      while (rs.next()) {
        ids.add(rs.getInt(1));
      }
    } catch (Exception e) {
      LOGGER.error("Unable to execute, {}", sql, e);
    }
    return ids;
  }

  int update(String sql, ParameterMapper mapper) {
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);) {
      return mapper.map(stmt).executeUpdate();
    } catch (Exception e) {
      LOGGER.error("Unable to execute, {}", sql, e);
      return -1;
    }
  }

  <T> List<T> query(String sql, ParameterMapper pm, ObjectMapper<T> obj) {
    List<T> results = new ArrayList<>();
    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);) {
      ResultSet rs = mapAndExecute(pm, stmt);
      while (rs.next()) {
        results.add(obj.map(rs));
      }
    } catch (Exception e) {
      LOGGER.error("Unable to execute, {}", sql, e);
    }
    return results;
  }

  private ResultSet mapAndExecute(ParameterMapper pm, PreparedStatement stmt) throws SQLException {
    if (pm != null) {
      return pm.map(stmt).executeQuery();
    } else {
      return stmt.executeQuery();
    }
  }

  @FunctionalInterface
  public interface ParameterMapper {
    PreparedStatement map(PreparedStatement s) throws SQLException;
  }

  @FunctionalInterface
  public interface ObjectMapper<T> {
    T map(ResultSet rs) throws SQLException;
  }
}
