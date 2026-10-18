package com.example.spacecatsmarket.integration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class InventoryClient {
	private final URI baseUri;
	private final HttpClient httpClient = HttpClient.newHttpClient();

	public InventoryClient(URI baseUri) {
		this.baseUri = baseUri;
	}

	public boolean isAvailable(String productId) {
		if (!productId.matches("[A-Za-z0-9-]+")) {
			throw new IllegalArgumentException("Invalid product id");
		}
		HttpRequest request = HttpRequest.newBuilder(baseUri.resolve("/inventory/" + productId)).GET().build();
		try {
			int status = httpClient.send(request, HttpResponse.BodyHandlers.discarding()).statusCode();
			if (status == 404) {
				return false;
			}
			if (status < 200 || status >= 300) {
				throw new IllegalStateException("Inventory service returned " + status);
			}
			return true;
		} catch (InterruptedException exception) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Inventory request was interrupted", exception);
		} catch (IOException exception) {
			throw new IllegalStateException("Inventory service is unavailable", exception);
		}
	}
}
