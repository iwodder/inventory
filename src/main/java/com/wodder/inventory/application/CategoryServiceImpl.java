package com.wodder.inventory.application;

import com.wodder.inventory.domain.entities.*;
import com.wodder.inventory.models.*;
import com.wodder.inventory.persistence.*;

import java.util.*;

public class CategoryServiceImpl implements CategoryService {

	private final Repository<Category> categoryRepository;

	CategoryServiceImpl(Repository<Category> categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Optional<CategoryModel> createCategory(String name) {
		Category c = categoryRepository.createItem(new Category(name));
		return c == null ? Optional.empty() : Optional.of(c.toModel());
	}

	@Override
	public Optional<CategoryModel> loadCategory(String id) {
		try {
			return categoryRepository.loadById(Long.parseLong(id)).map(Category::toModel);
		} catch (NumberFormatException e) {
			//TODO: Warn number couldn't be parsed
			return Optional.empty();
		}
	}
}
