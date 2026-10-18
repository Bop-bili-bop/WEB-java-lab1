package com.example.spacecatsmarket.application;

import com.example.spacecatsmarket.domain.Product;
import java.util.List;

public record ProductPage(
		List<Product> content,
		int page,
		int size,
		long totalElements
) {
	public ProductPage {
		content = List.copyOf(content);
	}
}
