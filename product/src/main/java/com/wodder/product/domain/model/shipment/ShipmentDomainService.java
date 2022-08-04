package com.wodder.product.domain.model.shipment;

import com.wodder.product.application.ReceiveShipmentCommand;
import java.util.ArrayList;
import java.util.List;

public class ShipmentDomainService {
  private ShipmentDomainService() {}

  public static Shipment addLineItems(Shipment from, ReceiveShipmentCommand cmd) {
    List<LineItem> items = new ArrayList<>();
    cmd.getItems()
        .forEachRemaining(i ->
            items.add(LineItem.builder()
            .withId(i.getId())
            .withName(i.getName())
            .withCasePack(i.getItemsPerCase())
            .withUnits(i.getUnits())
            .withItemPrice(i.getItemPrice())
            .withCasePrice(i.getCasePrice())
            .build())
        );
    return from.withLineItems(items);
  }
}
