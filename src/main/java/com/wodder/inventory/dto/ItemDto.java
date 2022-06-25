package com.wodder.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemDto {
  @JsonProperty
  private String id;
  @JsonProperty
  private String productId;
  @JsonProperty
  private String name;
  @JsonProperty
  private String location;

  private ItemDto(Builder b) {
    this.id = b.id;
    this.productId = b.productId;
    this.name = b.name;
    this.location = b.location;
  }

  private ItemDto() {
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public String getId() {
    return id;
  }

  public String getProductId() {
    return productId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String id;
    private String productId;
    private String name;
    private String location;

    private Builder() {
    }

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withProductId(String id) {
      this.productId = id;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withLocation(String location) {
      this.location = location;
      return this;
    }

    public ItemDto build() {
      return new ItemDto(this);
    }
  }
}
