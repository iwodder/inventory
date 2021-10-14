package com.wodder.inventory.console.models;

import com.wodder.inventory.dtos.*;

public interface InventoryItemModel {

	void createItem(InventoryItemDto itemDto);

	void registerListener(Listener listener);
}
