package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

import java.time.*;
import java.util.*;
import java.util.function.*;

public class Inventory extends Entity<InventoryId> {
	private final LocalDate date;
	private final Map<String, List<InventoryCount>> items;
	private final BiPredicate<InventoryCount, String> nameFilter = (InventoryCount i, String name) -> {
		if (i.getName() != null) {
			return i.getName().equals(name);
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
		items = new HashMap<>();
		this.date = date;
		this.state = state;
	}

	public Inventory(InventoryModel model) {
		super(InventoryId.inventoryIdOf(model.getId()));
		this.state = InventoryState.valueOf(model.getState());
		this.date = model.getInventoryDate();
		this.items = new HashMap<>();
		model.items().map(InventoryCount::new).forEach(this::addInventoryCount);
	}

	public Inventory(Inventory that) {
		super(that.id);
		this.date = that.date;
		this.items = new HashMap<>();
		for (String i : that.items.keySet()) {
			List<InventoryCount> newItems = new ArrayList<>();
			for (InventoryCount cnt : that.items.get(i)) {
				newItems.add(new InventoryCount(cnt));
			}
			this.items.put(i, newItems);
		}
	}

	public LocalDate date() {
		return date;
	}

	public void addInventoryCount(InventoryCount item) {
		state.addCount(items, item);
	}

	public int numberOfItems() {
		return items.values().stream().mapToInt(List::size).sum();
	}

	public List<InventoryCount> getItemsByCategory(String category) {
		if (items.containsKey(category.toUpperCase())) {
			return Collections.unmodifiableList(items.get(category.toUpperCase()));
		} else {
			return Collections.emptyList();
		}
	}

	public Optional<InventoryCount> getCount(String name) {
		return items.values().stream()
				.flatMap(List::stream)
				.filter(item -> nameFilter.test(item, name))
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
		items.values().stream()
				.flatMap(List::stream)
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
			void addCount(Map<String, List<InventoryCount>> items, InventoryCount item) {
				if (item == null) return;
				String key = item.getCategory().toUpperCase();
				if (items.containsKey(key)) {
					items.get(key).add(item);
				} else {
					List<InventoryCount> i = new ArrayList<>();
					i.add(item);
					items.put(key, i);
				}
			}
		},
		CLOSED {
			@Override
			boolean isOpen() {
				return false;
			}

			@Override
			void addCount(Map<String, List<InventoryCount>> items, InventoryCount item) {
				throw new IllegalStateException("Cannot add a new count to a closed inventory");
			}
		};

		abstract boolean isOpen();

		abstract void addCount(Map<String, List<InventoryCount>> items, InventoryCount item);
	}
}
