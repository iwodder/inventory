package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.*;
import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

	LocationService locationService;

	@BeforeEach
	void setup() {
		PersistenceFactory psf = TestPersistenceFactory.getUnpopulated();
		locationService = new LocationServiceImpl(psf.getRepository(Location.class));
	}

	@Test
	void create_location() {
		Optional<LocationModel> opt = locationService.createLocation("Freezer");
		assertTrue(opt.isPresent());
		assertEquals("Freezer", opt.get().getName());
	}

	@Test
	void load_location() {
		LocationModel l = locationService.createLocation("Freezer").get();

		Optional<LocationModel> result = locationService.loadLocation(l.getId());
		assertTrue(result.isPresent());
		assertEquals("Freezer", result.get().getName());
	}

	@Test
	void load_locations() {
		locationService.createLocation("Freezer");
		locationService.createLocation("Dry Goods");
		locationService.createLocation("Refrigerator");
		List<LocationModel> locations = locationService.loadLocations();
		assertEquals(3, locations.size());
	}

	@Test
	void creating_existing_location_returns_empty_optional() {
		locationService.createLocation("Pantry");
		assertTrue(locationService.createLocation("Pantry").isEmpty());
	}
}
