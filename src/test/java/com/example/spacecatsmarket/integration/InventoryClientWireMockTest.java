package com.example.spacecatsmarket.integration;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.serverError;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InventoryClientWireMockTest {
	@RegisterExtension
	static final WireMockExtension wireMock = WireMockExtension.newInstance()
			.options(wireMockConfig().dynamicPort())
			.build();

	@Test
	void stubsAvailableProduct() {
		wireMock.stubFor(get("/inventory/prd-star-yarn").willReturn(ok()));

		assertThat(client().isAvailable("prd-star-yarn")).isTrue();
	}

	@Test
	void stubsMissingProduct() {
		wireMock.stubFor(get("/inventory/missing").willReturn(notFound()));

		assertThat(client().isAvailable("missing")).isFalse();
	}

	@Test
	void stubsInventoryFailure() {
		wireMock.stubFor(get("/inventory/prd-star-yarn").willReturn(serverError()));

		assertThatThrownBy(() -> client().isAvailable("prd-star-yarn"))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("500");
	}

	private InventoryClient client() {
		return new InventoryClient(URI.create(wireMock.baseUrl()));
	}
}
