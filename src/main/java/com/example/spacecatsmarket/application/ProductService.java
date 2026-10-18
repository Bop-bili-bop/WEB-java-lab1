package com.example.spacecatsmarket.application;

import com.example.spacecatsmarket.application.exception.ProductNotFoundException;
import com.example.spacecatsmarket.domain.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

	private final Map<String, Product> products = new ConcurrentSkipListMap<>();

	public ProductService() {
		seedProducts();
	}

	public ProductPage findAll(int page, int size) {
		List<Product> allProducts = List.copyOf(products.values());

		int fromIndex = (int) Math.min((long) page * size, allProducts.size());
		int toIndex = Math.min(fromIndex + size, allProducts.size());

		return new ProductPage(
				allProducts.subList(fromIndex, toIndex),
				page,
				size,
				allProducts.size()
		);
	}

	public Product findById(String id) {
		return getProductOrThrow(id);
	}

	public Product create(ProductCommand command) {
		String id = UUID.randomUUID().toString();
		Product product = toProduct(id, command);
		products.put(id, product);
		return product;
	}

	public Product update(String id, ProductCommand command) {
		Product updatedProduct = toProduct(id, command);
		if (products.replace(id, updatedProduct) == null) {
			throw new ProductNotFoundException(id);
		}
		return updatedProduct;
	}

	public void delete(String id) {
		if (products.remove(id) == null) {
			throw new ProductNotFoundException(id);
		}
	}

	private Product getProductOrThrow(String id) {
		Product product = products.get(id);
		if (product == null) {
			throw new ProductNotFoundException(id);
		}
		return product;
	}

	private Product toProduct(String id, ProductCommand command) {
		return new Product(
				id,
				command.name(),
				command.description(),
				command.price(),
				command.categoryId(),
				command.sellerEmail(),
				command.stockQuantity()
		);
	}

	private void seedProducts() {
		List<Product> mockProducts = List.of(new Product(
				"prd-star-yarn",
				"Star Antigravity Yarn",
				"Self-floating yarn ball for zero-gravity cat lounges.",
				new BigDecimal("29.99"),
				"cat-toys",
				"vendor@cosmo-cats.market",
				42
		), new Product(
				"prd-galaxy-milk",
				"Galaxy Milk",
				"Lactose-free cosmic milk enriched with meteor minerals.",
				new BigDecimal("12.50"),
				"cat-food",
				"milkbar@cosmo-cats.market",
				128
		), new Product(
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
