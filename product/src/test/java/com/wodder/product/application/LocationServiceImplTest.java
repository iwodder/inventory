package com.wodder.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wodder.product.domain.model.product.Location;
import com.wodder.product.dto.LocationModel;
import com.wodder.product.persistence.PersistenceFactory;
import com.wodder.product.persistence.TestPersistenceFactory;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
    Assertions.assertEquals("Freezer", opt.get().getName());
  }

  @Test
  void load_location() {
    LocationModel l = locationService.createLocation("Freezer").get();

    Optional<LocationModel> result = locationService.loadLocation(l.getId());
    assertTrue(result.isPresent());
    Assertions.assertEquals("Freezer", result.get().getName());
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
