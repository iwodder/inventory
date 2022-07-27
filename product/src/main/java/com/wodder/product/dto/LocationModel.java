package com.wodder.product.dto;


import com.wodder.product.domain.model.product.Location;

public class LocationModel {
  private String name;
  private String id;

  public LocationModel() {
  }

  public LocationModel(Location location) {
    this.name = location.getName();
    this.id = location.getId().getId();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "LocationModel{" + "name='" + name + '\'' + ", id=" + id + '}';
  }
}
