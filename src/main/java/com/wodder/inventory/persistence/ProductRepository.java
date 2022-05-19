package com.wodder.inventory.persistence;


import com.wodder.inventory.domain.model.product.*;

import java.util.*;

public interface ProductRepository {

	List<Product> loadActiveItems();
}
