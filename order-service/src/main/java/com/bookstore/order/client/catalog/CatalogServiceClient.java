package com.bookstore.order.client.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class CatalogServiceClient {

    private static final Logger log = LoggerFactory.getLogger(CatalogServiceClient.class);
    private final RestClient restClient;


    public CatalogServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public Optional<Product> getProductByCode(String code) {
        log.info("Fetching product for code: {}", code);
        try {
            Product product = restClient
                    .get()
                    .uri("/api/products/{code}", code)
                    .retrieve()
                    .body(Product.class);
            return Optional.ofNullable(product);
        } catch (Exception ex) {
            log.error("Error fetching product for code: {}", code, ex);
            return Optional.empty();
        }

    }
}
