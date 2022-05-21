package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.product.Location;
import com.wodder.inventory.domain.model.product.LocationId;

public final class InMemoryLocationRepository extends InMemoryRepository<Location, LocationId> {

  InMemoryLocationRepository() {
  }
}
