package com.bookstore.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
