package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.*;
import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.util.*;
import java.util.stream.*;

public class LocationServiceImpl implements LocationService {

	private final Repository<Location, LocationId> locationRepository;

	LocationServiceImpl(Repository<Location, LocationId> locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public Optional<LocationModel> createLocation(String name) {
		if (locationRepository.loadByItem(new Location(name)).isEmpty()) {
			Location l = locationRepository.createItem(new Location(name));
			return l == null ? Optional.empty() : Optional.of(l.toModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<LocationModel> loadLocation(String id) {
		return locationRepository.loadById(LocationId.LocationIdOf(id)).map(Location::toModel);
	}

	@Override
	public List<LocationModel> loadLocations() {
		return locationRepository.loadAllItems()
				.stream()
				.map(Location::toModel)
				.collect(Collectors.toList());
	}
}
