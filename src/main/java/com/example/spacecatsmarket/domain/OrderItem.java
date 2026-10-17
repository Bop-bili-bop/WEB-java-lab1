package com.example.spacecatsmarket.domain;

import java.util.Objects;

public class OrderItem {

	private final String productId;
	private final int quantity;

	public OrderItem(String productId, int quantity) {
		this.productId = Objects.requireNonNull(productId, "productId must not be null");
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}
}
