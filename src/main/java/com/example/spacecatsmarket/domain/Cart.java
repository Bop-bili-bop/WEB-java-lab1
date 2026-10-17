package com.example.spacecatsmarket.domain;

import java.util.List;
import java.util.Objects;

public class Cart {

	private final String id;
	private final String customerEmail;
	private final List<OrderItem> items;

	public Cart(String id, String customerEmail, List<OrderItem> items) {
		this.id = Objects.requireNonNull(id, "id must not be null");
		this.customerEmail = Objects.requireNonNull(customerEmail, "customerEmail must not be null");
		this.items = List.copyOf(items);
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
}
