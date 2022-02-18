package com.wodder.inventory.domain.entities;

import com.wodder.inventory.models.*;

import java.time.*;
import java.util.*;
import java.util.function.*;

public class Inventory extends Entity {
	private final LocalDate date;
	private final Map<String, List<Count>> items;
	private final BiPredicate<Count, String> nameFilter = (Count i, String name) -> {
		if (i.getName() != null) {
			return i.getName().equals(name);
		} else {
			return false;
		}
	};
	private InventoryState state;

	public Inventory() {
		this(0L, LocalDate.now(), InventoryState.OPEN);
	}

	public Inventory(LocalDate date) {
		this(0L, date, null);
	}

	private Inventory(Long id, LocalDate date, InventoryState state) {
		super(id);
		items = new HashMap<>();
		this.date = date;
		this.state = state;
	}

	public Inventory(InventoryModel model) {
		super(model.getId());
		this.state = InventoryState.valueOf(model.getState());
		this.date = model.getInventoryDate();
		this.items = new HashMap<>();
		model.items().map(Count::new).forEach(this::addInventoryCount);
	}

	public Inventory(Inventory that) {
		super(that.id);
		this.date = that.date;
		this.items = new HashMap<>();
		for (String i : that.items.keySet()) {
			List<Count> newItems = new ArrayList<>();
			for (Count cnt : that.items.get(i)) {
				newItems.add(new Count(cnt));
			}
			this.items.put(i, newItems);
		}
	}

	public LocalDate date() {
		return date;
	}

	public void addInventoryCount(Count item) {
		state.addCount(items, item);
	}

	public int numberOfItems() {
		return items.values().stream().mapToInt(List::size).sum();
	}

	public List<Count> getItemsByCategory(String category) {
		if (items.containsKey(category.toUpperCase())) {
			return Collections.unmodifiableList(items.get(category.toUpperCase()));
		} else {
			return Collections.emptyList();
		}
	}

	public Optional<Count> getCount(String name) {
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
		InventoryModel result = new InventoryModel(id, state.name());
		result.setInventoryDate(date);
		items.values().stream()
				.flatMap(List::stream)
				.map(Count::toModel)
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
			void addCount(Map<String, List<Count>> items, Count item) {
				if (item == null) return;
				String key = item.getCategory().toUpperCase();
				if (items.containsKey(key)) {
					items.get(key).add(item);
				} else {
					List<Count> i = new ArrayList<>();
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
			void addCount(Map<String, List<Count>> items, Count item) {
				throw new IllegalStateException("Cannot add a new count to a closed inventory");
			}
		};

		abstract boolean isOpen();

		abstract void addCount(Map<String, List<Count>> items, Count item);
	}
}
