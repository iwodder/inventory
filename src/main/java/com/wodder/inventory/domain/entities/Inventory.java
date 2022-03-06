package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

import java.time.*;
import java.util.*;
import java.util.function.*;

public class Inventory extends Entity<InventoryId> {
	private final LocalDate date;
	private final List<InventoryCount> counts = new ArrayList<>();

	private final BiPredicate<InventoryCount, ProductId> idFilter = (InventoryCount i, ProductId id) -> {
		if (i.getProductId() != null) {
			return i.getProductId().equals(id);
		} else {
			return false;
		}
	};
	private InventoryState state;

	public Inventory() {
		this(LocalDate.now());
	}

	public Inventory(LocalDate date) {
		this(new InventoryId(), date, InventoryState.OPEN);
	}

	private Inventory(InventoryId id, LocalDate date, InventoryState state) {
		super(id);
		this.date = date;
		this.state = state;
	}

	public Inventory(InventoryModel model) {
		super(InventoryId.inventoryIdOf(model.getId()));
		this.state = InventoryState.valueOf(model.getState());
		this.date = model.getInventoryDate();
		model.items().map(InventoryCount::new).forEach(this::addInventoryCount);
	}

	public Inventory(Inventory that) {
		super(that.id);
		this.date = that.date;
		that.counts.stream().map(InventoryCount::new).forEach(this.counts::add);
	}

	public LocalDate date() {
		return date;
	}

	public void addInventoryCount(InventoryCount item) {
		state.addCount(counts, item);
	}

	public int numberOfItems() {
		return counts.size();
	}

	public Optional<InventoryCount> getCount(ProductId productId) {
		return counts.stream()
				.filter(item -> idFilter.test(item, productId))
				.findAny();
	}

	public LocalDate getInventoryDate() {
		return date;
	}

	public boolean isOpen() {
		return state.isOpen();
	}

	public void finish() {
		this.state = InventoryState.CLOSED;
	}

	public InventoryModel toModel() {
		InventoryModel result = new InventoryModel(id.getId(), state.name());
		result.setInventoryDate(date);
		counts.stream()
				.map(InventoryCount::toModel)
				.forEach(result::addInventoryCountModel);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Inventory)) return false;
		Inventory inventory = (Inventory) o;
		return Objects.equals(date, inventory.date) && Objects.equals(id, inventory.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(date);
	}

	private enum InventoryState {
		OPEN {
			@Override
			boolean isOpen() {
				return true;
			}

			@Override
			void addCount(List<InventoryCount> items, InventoryCount item) {
				if (item == null) return;
				items.add(item);
			}
		},
		CLOSED {
			@Override
			boolean isOpen() {
				return false;
			}

			@Override
			void addCount(List<InventoryCount> items, InventoryCount item) {
				throw new IllegalStateException("Cannot add a new count to a closed inventory");
			}
		};

		abstract boolean isOpen();

		abstract void addCount(List<InventoryCount> items, InventoryCount item);
	}
}
