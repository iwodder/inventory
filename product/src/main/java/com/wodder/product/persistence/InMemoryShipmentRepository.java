package com.wodder.product.persistence;

import com.wodder.product.domain.model.shipment.Shipment;
import com.wodder.product.domain.model.shipment.ShipmentId;

public final class InMemoryShipmentRepository
    extends InMemoryRepository<Shipment, ShipmentId> implements ShipmentRepository {

}
