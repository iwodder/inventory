package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.entities.*;

import java.util.*;

public interface ProductRepository {

	List<Product> loadActiveItems();
}
