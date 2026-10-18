package com.example.spacecatsmarket.api.dto;

import com.example.spacecatsmarket.api.validation.CosmicWordCheck;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequest(
		@NotBlank
		@Size(min = 3, max = 120)
		@CosmicWordCheck
		String name,

		@NotBlank
		@Size(min = 10, max = 1000)
		String description,

		@NotNull
		@DecimalMin(value = "0.01")
		BigDecimal price,

		@NotBlank
		String categoryId,

		@NotBlank
		@Email
		String sellerEmail,

		@NotNull
		@Min(0)
		Integer stockQuantity
) {
}
