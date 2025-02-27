package com.example.bookstore.catalog.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {
                "spring.test.database.replace=none",
                "spring.datasource.url=jdbc:tc:postgresql:17-alpine:///db",
        })
//@Import(TestcontainersConfiguration.class)
@Sql("/test-data.sql")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        assertThat(products).hasSize(25);
    }

    @Test
    void shouldGetProductByCode() {
        ProductEntity product = productRepository.findByCode("P122").orElseThrow();
        assertThat(product.getCode()).isEqualTo("P122");
        assertThat(product.getName()).isEqualTo("Brave New World");
        assertThat(product.getDescription()).isEqualTo("A futuristic novel depicting a technologically advanced society with a dark underside.");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("16.0"));
    }

    @Test
    void shouldReturnEmptyWhenProductCodeNotExists() {
        assertThat(productRepository.findByCode("invalid_product_code")).isEmpty();
    }

}