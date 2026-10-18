package com.example.spacecatsmarket.application;

import java.math.BigDecimal;

public record ProductCommand(
		String name,
		String description,
		BigDecimal price,
		String categoryId,
		String sellerEmail,
		int stockQuantity
) {
}
