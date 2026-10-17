package com.example.spacecatsmarket.domain;

import java.util.Objects;

public class Category {

	private final String id;
	private final String name;
	private final String description;

	public Category(String id, String name, String description) {
		this.id = Objects.requireNonNull(id, "id must not be null");
		this.name = Objects.requireNonNull(name, "name must not be null");
		this.description = Objects.requireNonNull(description, "description must not be null");
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
