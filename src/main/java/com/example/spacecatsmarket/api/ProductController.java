package com.example.spacecatsmarket.api;

import com.example.spacecatsmarket.api.dto.PagedResponse;
import com.example.spacecatsmarket.api.dto.ProductRequest;
import com.example.spacecatsmarket.api.dto.ProductResponse;
import com.example.spacecatsmarket.api.mapper.ProductMapper;
import com.example.spacecatsmarket.application.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Validated
@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;
	private final ProductMapper productMapper;

	public ProductController(ProductService productService, ProductMapper productMapper) {
		this.productService = productService;
		this.productMapper = productMapper;
	}

	@GetMapping
	public PagedResponse<ProductResponse> findAll(
			@RequestParam(defaultValue = "0") @Min(0) int page,
			@RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
	) {
		return productMapper.toDto(productService.findAll(page, size));
	}

	@GetMapping("/{id}")
	public ProductResponse findById(@PathVariable String id) {
		return productMapper.toDto(productService.findById(id));
	}

	@PostMapping
	public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
		ProductResponse response = productMapper.toDto(productService.create(productMapper.toCommand(request)));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(response.id())
				.toUri();

		return ResponseEntity.created(location).body(response);
	}

	@PutMapping("/{id}")
	public ProductResponse update(@PathVariable String id, @Valid @RequestBody ProductRequest request) {
		return productMapper.toDto(productService.update(id, productMapper.toCommand(request)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
