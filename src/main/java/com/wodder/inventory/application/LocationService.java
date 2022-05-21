package com.wodder.inventory.application;

import com.wodder.inventory.dto.LocationModel;
import java.util.List;
import java.util.Optional;

public interface LocationService {

  Optional<LocationModel> createLocation(String name);

  Optional<LocationModel> loadLocation(String id);

  List<LocationModel> loadLocations();
}
