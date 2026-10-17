package com.example.spacecatsmarket.api.dto;

import java.math.BigDecimal;

public record ProductResponse(
		String id,
		String name,
		String description,
		BigDecimal price,
		String categoryId,
		String sellerEmail,
		int stockQuantity
) {
}
