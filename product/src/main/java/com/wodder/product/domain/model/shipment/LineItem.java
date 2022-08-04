package com.wodder.product.domain.model.shipment;

public class LineItem {
  private final ItemId id;
  private final String name;
  private final QtyRecieved qty;
  private final Price price;
  private final Units units;
  private final CasePack pack;

  public ItemId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public QtyRecieved getQty() {
    return qty;
  }

  public Price getPrice() {
    return price;
  }

  public Units getUnits() {
    return units;
  }

  public CasePack getPack() {
    return pack;
  }

  private LineItem(Builder b) {
    this.id = b.itemId;
    this.name = b.name;
    this.qty = b.qtyRecieved;
    this.price = b.price;
    this.units = b.units;
    this.pack = b.casePack;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private ItemId itemId;
    private String name;
    private QtyRecieved qtyRecieved;
    private Price price;
    private Units units;
    private CasePack casePack;

    private Builder() {}

    public Builder withId(String id) {
      this.itemId = ItemId.of(id);
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withItemPrice(String itemPrice) {
      this.price = Price.of(itemPrice, "0");
      return this;
    }

    public Builder withCasePrice(String casePrice) {
      this.price = Price.of(price.getItemPrice(), casePrice);
      return this;
    }

    public Builder withCasePack(String itemsPerCase) {
      this.casePack = CasePack.of(itemsPerCase);
      return this;
    }

    public Builder withUnits(String units) {
      this.units = Units.of(units);
      return this;
    }

    public LineItem build() {
      return new LineItem(this);
    }
  }
}
