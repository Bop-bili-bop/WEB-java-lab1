package com.example.spacecatsmarket.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class Order {

	private final String id;
	private final String customerEmail;
	private final List<OrderItem> items;
	private final BigDecimal totalPrice;
	private final Instant createdAt;

	public Order(String id, String customerEmail, List<OrderItem> items, Instant createdAt) {
		this.id = Objects.requireNonNull(id, "id must not be null");
		this.customerEmail = Objects.requireNonNull(customerEmail, "customerEmail must not be null");
		if (customerEmail.isBlank()) {
			throw new IllegalArgumentException("customerEmail must not be blank");
		}
		this.items = List.copyOf(items);
		if (this.items.isEmpty()) {
			throw new IllegalArgumentException("items must not be empty");
		}
		this.totalPrice = this.items.stream()
				.map(OrderItem::getSubtotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
	}

	public String getId() {
		return id;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
