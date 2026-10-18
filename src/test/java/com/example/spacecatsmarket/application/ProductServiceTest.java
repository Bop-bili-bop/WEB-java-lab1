package com.example.spacecatsmarket.application;

import com.example.spacecatsmarket.application.exception.ProductNotFoundException;
import com.example.spacecatsmarket.domain.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductServiceTest {
	private ProductService service;
	private ProductCommand command;

	@BeforeEach
	void setUp() {
		service = new ProductService();
		command = new ProductCommand(
				"Lunar Lamp",
				"A durable orbital lamp",
				new BigDecimal("49.90"),
				"cat-furniture",
				"seller@example.com",
				5
		);
	}

	@Test
	void returnsRequestedPageIncludingEmptyPage() {
		ProductPage firstPage = service.findAll(0, 2);
		ProductPage emptyPage = service.findAll(10, 2);

		assertThat(firstPage.content()).hasSize(2);
		assertThat(firstPage.totalElements()).isEqualTo(3);
		assertThat(emptyPage.content()).isEmpty();
	}

	@Test
	void findsProductOrReportsMissingId() {
		assertThat(service.findById("prd-star-yarn").getName()).contains("Star");
		assertThatThrownBy(() -> service.findById("missing"))
				.isInstanceOf(ProductNotFoundException.class)
				.hasMessageContaining("missing");
	}

	@Test
	void createsProductWithGeneratedId() {
		Product created = service.create(command);

		assertThat(created.getId()).isNotBlank();
		assertThat(service.findById(created.getId())).isSameAs(created);
	}

	@Test
	void updatesExistingProductOrReportsMissingId() {
		Product updated = service.update("prd-star-yarn", command);

		assertThat(updated.getId()).isEqualTo("prd-star-yarn");
		assertThat(updated.getName()).isEqualTo("Lunar Lamp");
		assertThatThrownBy(() -> service.update("missing", command))
				.isInstanceOf(ProductNotFoundException.class);
	}

	@Test
	void deletesExistingProductOrReportsMissingId() {
		service.delete("prd-star-yarn");

		assertThatThrownBy(() -> service.findById("prd-star-yarn"))
				.isInstanceOf(ProductNotFoundException.class);
		assertThatThrownBy(() -> service.delete("missing"))
				.isInstanceOf(ProductNotFoundException.class);
	}
}
