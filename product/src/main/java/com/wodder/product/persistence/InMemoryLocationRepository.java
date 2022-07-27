package com.wodder.product.persistence;

import com.wodder.product.domain.model.product.Location;
import com.wodder.product.domain.model.product.LocationId;

public final class InMemoryLocationRepository extends InMemoryRepository<Location, LocationId> {

  InMemoryLocationRepository() {
  }
}
