package com.wodder.inventory.application;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

	@Mock
	Repository<Location, LocationId> locationRepository;

	@InjectMocks
	LocationServiceImpl locationService;

	@Captor
	ArgumentCaptor<Location> locationArgumentCaptor;

	@Test
	void create_location() {
		locationService.createLocation("Freezer");
		verify(locationRepository).createItem(locationArgumentCaptor.capture());

		Location l = locationArgumentCaptor.getValue();
		assertEquals("Freezer", l.getName());
	}

	@Test
	void load_location() {
		locationService.loadLocation("1");
		verify(locationRepository).loadById(LocationId.LocationIdOf("1"));
	}

	@Test
	void load_locations() {
		when(locationRepository.loadAllItems()).thenReturn(Arrays.asList(new Location("Pantry"), new Location("Freezer")));
		List<LocationModel> locations = locationService.loadLocations();
		assertEquals(2, locations.size());
	}

	@Test
	void creating_existing_location_returns_empty_optional() {
		when(locationRepository.loadByItem(new Location("Pantry"))).thenReturn(Optional.of(new Location("Pantry")));
		assertTrue(locationService.createLocation("Pantry").isEmpty());
	}
}
