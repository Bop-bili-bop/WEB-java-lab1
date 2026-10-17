package com.example.spacecatsmarket.application.mapper;

import com.example.spacecatsmarket.api.dto.ProductRequest;
import com.example.spacecatsmarket.api.dto.ProductResponse;
import com.example.spacecatsmarket.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

	public ProductResponse toDto(Product product) {
		return new ProductResponse(
				product.getId(),
				product.getName(),
				product.getDescription(),
				product.getPrice(),
				product.getCategoryId(),
				product.getSellerEmail(),
				product.getStockQuantity()
		);
	}

	public Product toDomain(String id, ProductRequest request) {
		return new Product(
				id,
				request.name(),
				request.description(),
				request.price(),
				request.categoryId(),
				request.sellerEmail(),
				request.stockQuantity()
		);
	}
}
