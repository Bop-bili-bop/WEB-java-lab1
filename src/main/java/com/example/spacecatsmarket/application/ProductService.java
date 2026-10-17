package com.example.spacecatsmarket.application;

import com.example.spacecatsmarket.api.dto.PagedResponse;
import com.example.spacecatsmarket.api.dto.ProductRequest;
import com.example.spacecatsmarket.api.dto.ProductResponse;
import com.example.spacecatsmarket.application.exception.ProductNotFoundException;
import com.example.spacecatsmarket.application.mapper.ProductMapper;
import com.example.spacecatsmarket.domain.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	private final ProductMapper productMapper;
	private final Map<String, Product> products = new LinkedHashMap<>();

	public ProductService(ProductMapper productMapper) {
		this.productMapper = productMapper;
		seedProducts();
	}

	public PagedResponse<ProductResponse> findAll(int page, int size) {
		List<ProductResponse> allProducts = products.values().stream()
				.map(productMapper::toDto)
				.toList();

		int fromIndex = Math.min(page * size, allProducts.size());
		int toIndex = Math.min(fromIndex + size, allProducts.size());

		return new PagedResponse<>(
				allProducts.subList(fromIndex, toIndex),
				page,
				size,
				allProducts.size()
		);
	}

	public ProductResponse findById(String id) {
		return productMapper.toDto(getProductOrThrow(id));
	}

	public ProductResponse create(ProductRequest request) {
		String id = UUID.randomUUID().toString();
		Product product = productMapper.toDomain(id, request);
		products.put(id, product);
		return productMapper.toDto(product);
	}

	public ProductResponse update(String id, ProductRequest request) {
		getProductOrThrow(id);
		Product updatedProduct = productMapper.toDomain(id, request);
		products.put(id, updatedProduct);
		return productMapper.toDto(updatedProduct);
	}

	public void delete(String id) {
		getProductOrThrow(id);
		products.remove(id);
	}

	private Product getProductOrThrow(String id) {
		Product product = products.get(id);
		if (product == null) {
			throw new ProductNotFoundException(id);
		}
		return product;
	}

	private void seedProducts() {
		List<Product> mockProducts = new ArrayList<>();
		mockProducts.add(new Product(
				"prd-star-yarn",
				"Star Antigravity Yarn",
				"Self-floating yarn ball for zero-gravity cat lounges.",
				new BigDecimal("29.99"),
				"cat-toys",
				"vendor@cosmo-cats.market",
				42
		));
		mockProducts.add(new Product(
				"prd-galaxy-milk",
				"Galaxy Milk",
				"Lactose-free cosmic milk enriched with meteor minerals.",
				new BigDecimal("12.50"),
				"cat-food",
				"milkbar@cosmo-cats.market",
				128
		));
		mockProducts.add(new Product(
				"prd-comet-scratcher",
				"Comet Scratcher",
				"Durable asteroid-fiber scratching post for interstellar claws.",
				new BigDecimal("84.00"),
				"cat-furniture",
				"scratchers@cosmo-cats.market",
				9
		));

		mockProducts.forEach(product -> products.put(product.getId(), product));
	}
}
