package com.wodder.inventory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemDto {
  @JsonProperty
  private String id;
  @JsonProperty
  private String name;
  @JsonProperty
  private String location;

  private ItemDto(Builder b) {
    this.id = b.id;
    this.name = b.name;
    this.location = b.location;
  }

  public ItemDto() {
  }

  public String getName() {
    return name;
  }

  public String getLocation() {
    return location;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String id;
    private String name;
    private String location;

    private Builder() {
    }

    public Builder withId(String id) {
      this.id = id;
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
