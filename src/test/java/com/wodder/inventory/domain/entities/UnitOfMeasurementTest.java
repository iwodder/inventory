package com.wodder.inventory.domain.entities;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasurementTest {

	@Test
	@DisplayName("Creating a unit of measurement requires a unit")
	void unit_required() {
		assertThrows(IllegalArgumentException.class, () -> new UnitOfMeasurement((String) null));
		assertThrows(IllegalArgumentException.class, () -> new UnitOfMeasurement(""));
	}
}
