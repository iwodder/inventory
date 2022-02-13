package com.wodder.inventory.application;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.util.*;
import java.util.stream.*;

public class LocationServiceImpl implements LocationService {

	private final Repository<Location> locationRepository;

	LocationServiceImpl(Repository<Location> locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public Optional<LocationModel> createLocation(String name) {
		Location l = locationRepository.createItem(new Location(name));
		return l == null ? Optional.empty() : Optional.of(l.toModel());
	}

	@Override
	public Optional<LocationModel> loadLocation(String id) {
		try {
			return locationRepository.loadById(Long.parseLong(id)).map(Location::toModel);
		} catch (NumberFormatException e) {
			//TODO: Warn number couldn't be parsed
			return Optional.empty();
		}
	}

	@Override
	public List<LocationModel> loadLocations() {
		return locationRepository.loadAllItems()
				.stream()
				.map(Location::toModel)
				.collect(Collectors.toList());
	}
}
