package com.wodder.inventory.application;

import com.wodder.inventory.application.implementations.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

	private static final ServiceFactory svc = new ServiceFactoryImpl(new PersistenceFactoryImpl());

	LocationService locationService;

	@BeforeEach
	void setup() {
		new DataPopulation().init();
		locationService = svc.getService(LocationService.class);
	}

	@Test
	void create_location() {
		locationService.createLocation("Freezer");
	}

	@Test
	void load_location() {
		locationService.loadLocation("1");
	}

	@Test
	void load_locations() {
		List<LocationModel> locations = locationService.loadLocations();
		assertEquals(2, locations.size());
	}

	@Test
	void creating_existing_location_returns_empty_optional() {
		assertTrue(locationService.createLocation("Pantry").isEmpty());
	}
}
