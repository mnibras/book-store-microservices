package com.bookstore.order.web.controller;

import com.bookstore.order.AbstractIT;
import com.bookstore.order.testdata.TestDataFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

class OrderControllerIntegrationTest extends AbstractIT {

    @Nested
    class CreateOrderTests {

    }

    @Test
    void shouldCreateOrderSuccessfully() {
        mockGetProductByCode("P100", "Product 1", new BigDecimal("25.50"));
        var payload =
                """
                            {
                                "customer" : {
                                    "name": "Nibras",
                                    "email": "nibras@gmail.com",
                                    "phone": "12356789"
                                },
                                "deliveryAddress" : {
                                    "addressLine1": "Park Road",
                                    "addressLine2": "Random Street",
                                    "city": "New York",
                                    "state": "NY",
                                    "zipCode": "54613",
                                    "country": "USA"
                                },
                                "items": [
                                    {
                                        "code": "P100",
                                        "name": "Product 1",
                                        "price": 25.50,
                                        "quantity": 1
                                    }
                                ]
                            }
                        """;

        given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("orderNumber", notNullValue());
    }

    @Test
    void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
        var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
        given().contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/api/orders")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}