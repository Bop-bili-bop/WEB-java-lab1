package com.example.spacecatsmarket.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

	private final String id;
	private final String name;
	private final String description;
	private final BigDecimal price;
	private final String categoryId;
	private final String sellerEmail;
	private final int stockQuantity;

	public Product(
			String id,
			String name,
			String description,
			BigDecimal price,
			String categoryId,
			String sellerEmail,
			int stockQuantity
	) {
		this.id = requireNonBlank(id, "id");
		this.name = requireNonBlank(name, "name");
		this.description = requireNonBlank(description, "description");
		this.price = Objects.requireNonNull(price, "price must not be null");
		if (price.signum() <= 0) {
			throw new IllegalArgumentException("price must be greater than 0");
		}
		this.categoryId = requireNonBlank(categoryId, "categoryId");
		this.sellerEmail = requireNonBlank(sellerEmail, "sellerEmail");
		if (stockQuantity < 0) {
			throw new IllegalArgumentException("stockQuantity must not be negative");
		}
		this.stockQuantity = stockQuantity;
	}

	private static String requireNonBlank(String value, String field) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException(field + " must not be blank");
		}
		return value;
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

	public BigDecimal getPrice() {
		return price;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}
}
