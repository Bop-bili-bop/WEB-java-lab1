package com.example.spacecatsmarket;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerValidationTests {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void acceptsValidProduct() throws Exception {
		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "name":"Lunar Lamp",
						  "description":"A durable orbital lamp",
						  "price":49.90,
						  "categoryId":"cat-furniture",
						  "sellerEmail":"seller@example.com",
						  "stockQuantity":5
						}
						"""))
				.andExpect(status().isCreated())
				.andExpect(header().exists("Location"))
				.andExpect(jsonPath("$.name").value("Lunar Lamp"));
	}

	@Test
	void rejectsInvalidProduct() throws Exception {
		mockMvc.perform(post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
						{
						  "name":"Lamp",
						  "description":"short",
						  "price":0,
						  "categoryId":"",
						  "sellerEmail":"not-an-email",
						  "stockQuantity":-1
						}
						"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Validation failed"));
	}

	@Test
	void rejectsInvalidPagination() throws Exception {
		mockMvc.perform(get("/api/products").param("page", "-1").param("size", "101"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Validation failed"));
	}
}
