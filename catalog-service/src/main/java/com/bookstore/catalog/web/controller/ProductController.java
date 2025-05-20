package com.bookstore.catalog.web.controller;

import com.bookstore.catalog.domain.PagedResult;
import com.bookstore.catalog.domain.Product;
import com.bookstore.catalog.domain.ProductNotFoundException;
import com.bookstore.catalog.domain.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductService productService;
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("Fetching products for page: {}", pageNo);
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        log.info("Fetching products for code: {}", code);
        return productService.getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }

}
