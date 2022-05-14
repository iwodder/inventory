package com.wodder.inventory.application.implementations;

import com.wodder.inventory.application.*;
import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.util.*;
import java.util.stream.*;

public class CategoryServiceImpl implements CategoryService {

	private final Repository<Category, CategoryId> categoryRepository;

	CategoryServiceImpl(Repository<Category, CategoryId> categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Optional<CategoryModel> createCategory(String name) {
		if (categoryRepository.loadByItem(new Category(name)).isEmpty()) {
			Category c = categoryRepository.createItem(new Category(name));
			return c == null ? Optional.empty() : Optional.of(c.toModel());
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<CategoryModel> loadCategory(String id) {
		return categoryRepository.loadById(CategoryId.categoryIdOf(id)).map(Category::toModel);
	}

	@Override
	public List<CategoryModel> loadCategories() {
		return categoryRepository.loadAllItems()
				.stream()
				.map(Category::toModel)
				.collect(Collectors.toList());
	}
}
