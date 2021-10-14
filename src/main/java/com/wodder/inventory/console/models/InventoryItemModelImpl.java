package com.wodder.inventory.console.models;

import com.wodder.inventory.domain.*;
import com.wodder.inventory.dtos.*;

import java.util.*;

public class InventoryItemModelImpl implements InventoryItemModel {
	private Listener listener;
	private final ItemStorage storage;

	public InventoryItemModelImpl(ServiceFactory factory) {
		storage = factory.getService(ItemStorage.class);
	}

	@Override
	public void createItem(InventoryItemDto itemDto) {
		Optional<InventoryItemDto> result = storage.addItem(itemDto);
		if (result.isPresent()) {
			listener.onSuccess();
		} else {
			listener.onError();
		}
	}

	@Override
	public void registerListener(Listener listener) {
		this.listener = listener;
	}
}
