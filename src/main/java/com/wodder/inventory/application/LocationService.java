package com.wodder.inventory.application;

import com.wodder.inventory.dto.*;

import java.util.*;

public interface LocationService {

	Optional<LocationModel> createLocation(String name);

	Optional<LocationModel> loadLocation(String id);

	List<LocationModel> loadLocations();
}
