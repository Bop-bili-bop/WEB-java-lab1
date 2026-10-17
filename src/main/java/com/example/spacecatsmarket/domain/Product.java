package com.example.spacecatsmarket.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

	private String id;
	private String name;
	private String description;
	private BigDecimal price;
	private String categoryId;
	private String sellerEmail;
	private int stockQuantity;

	public Product(
			String id,
			String name,
			String description,
			BigDecimal price,
			String categoryId,
			String sellerEmail,
			int stockQuantity
	) {
		this.id = Objects.requireNonNull(id, "id must not be null");
		this.name = Objects.requireNonNull(name, "name must not be null");
		this.description = Objects.requireNonNull(description, "description must not be null");
		this.price = Objects.requireNonNull(price, "price must not be null");
		this.categoryId = Objects.requireNonNull(categoryId, "categoryId must not be null");
		this.sellerEmail = Objects.requireNonNull(sellerEmail, "sellerEmail must not be null");
		this.stockQuantity = stockQuantity;
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
