package com.wodder.inventory.console.models;

import com.wodder.inventory.dtos.*;

public interface InventoryItemModel {

	Result<InventoryItemDto, String> createItem(InventoryItemDto itemDto);
}
