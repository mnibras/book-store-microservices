package com.bookstore.order.domain;

import com.bookstore.order.client.catalog.CatalogServiceClient;
import com.bookstore.order.client.catalog.Product;
import com.bookstore.order.domain.model.CreateOrderRequest;
import com.bookstore.order.domain.model.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class OrderValidator {

    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

    private final CatalogServiceClient client;

    OrderValidator(CatalogServiceClient client) {
        this.client = client;
    }

    void validate(CreateOrderRequest request) {
        Set<OrderItem> items = request.items();
        for (OrderItem item : items) {
            Product product = client.getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid Product code: " + item.code()));
            if (item.price().compareTo(product.getPrice()) != 0) {
                log.error(
                        "Product price not matching. Actual price:{}, received price:{}",
                        product.getPrice(),
                        item.price());
                throw new InvalidOrderException("Product price not matching");
            }
        }
    }
}
