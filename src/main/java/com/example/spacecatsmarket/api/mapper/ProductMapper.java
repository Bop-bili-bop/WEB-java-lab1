package com.example.spacecatsmarket.api.mapper;

import com.example.spacecatsmarket.api.dto.PagedResponse;
import com.example.spacecatsmarket.api.dto.ProductRequest;
import com.example.spacecatsmarket.api.dto.ProductResponse;
import com.example.spacecatsmarket.application.ProductCommand;
import com.example.spacecatsmarket.application.ProductPage;
import com.example.spacecatsmarket.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

	public ProductCommand toCommand(ProductRequest request) {
		return new ProductCommand(
				request.name(),
				request.description(),
				request.price(),
				request.categoryId(),
				request.sellerEmail(),
				request.stockQuantity()
		);
	}

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

	public PagedResponse<ProductResponse> toDto(ProductPage page) {
		return new PagedResponse<>(
				page.content().stream().map(this::toDto).toList(),
				page.page(),
				page.size(),
				page.totalElements()
		);
	}
}
