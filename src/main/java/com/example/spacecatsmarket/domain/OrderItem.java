package com.example.spacecatsmarket.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {

	private final String productId;
	private final int quantity;
	private final BigDecimal unitPrice;

	public OrderItem(String productId, int quantity, BigDecimal unitPrice) {
		this.productId = Objects.requireNonNull(productId, "productId must not be null");
		if (productId.isBlank()) {
			throw new IllegalArgumentException("productId must not be blank");
		}
		if (quantity <= 0) {
			throw new IllegalArgumentException("quantity must be greater than 0");
		}
		this.quantity = quantity;
		this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice must not be null");
		if (unitPrice.signum() <= 0) {
			throw new IllegalArgumentException("unitPrice must be greater than 0");
		}
	}

	public String getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public BigDecimal getSubtotal() {
		return unitPrice.multiply(BigDecimal.valueOf(quantity));
	}
}
