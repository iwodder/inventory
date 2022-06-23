package com.wodder.inventory.persistence;

import com.wodder.inventory.domain.model.inventory.Item;
import com.wodder.inventory.domain.model.inventory.ItemId;
import javax.annotation.PostConstruct;
import javax.inject.Named;

@Named("InMemoryItemRepository")
public class InMemoryItemRepository extends InMemoryRepository<Item, ItemId> {
  InMemoryItemRepository() {
  }

  @PostConstruct
  void init() {
    createItem(Item.builder()
        .withId("item123")
        .withName("2% Milk")
        .withLocation("Refrigerator")
        .withUnits("Gallon")
        .build());
  }
}
