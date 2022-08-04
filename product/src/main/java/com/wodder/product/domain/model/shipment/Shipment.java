package com.wodder.product.domain.model.shipment;

import com.wodder.product.domain.model.product.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Shipment extends Entity<ShipmentId> {
  private final List<LineItem> lineItems;

  private Shipment(ShipmentId id) {
    super(id);
    this.lineItems = new ArrayList<>();
  }

  public Shipment(Shipment that) {
    super(that.id);
    this.lineItems = new ArrayList<>(that.lineItems);
  }

  public static final Shipment from(ShipmentId id) {
    return new Shipment(id);
  }

  public Shipment withLineItems(Collection<LineItem> items) {
    Shipment s = new Shipment(this);
    s.lineItems.addAll(items);
    return s;
  }

  public Iterable<LineItem> getLineItems() {
    return lineItems;
  }
}
