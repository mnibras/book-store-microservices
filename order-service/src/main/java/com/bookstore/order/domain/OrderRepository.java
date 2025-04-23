package com.bookstore.order.domain;

import com.bookstore.order.domain.model.OrderStatus;
import com.bookstore.order.domain.model.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByStatus(OrderStatus orderStatus);

    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    default void updateOrderStatus(String orderNumber, OrderStatus orderStatus) {
        OrderEntity order = this.findByOrderNumber(orderNumber).orElseThrow();
        order.setStatus(orderStatus);
        this.save(order);
    }

    @Query("""
            select new com.bookstore.order.domain.model.OrderSummary(o.orderNumber, o.status)
            from OrderEntity o
            where o.userName = :userName
            """)
    List<OrderSummary> findByUserName(String userName);

    @Query(
            """
            select distinct o
            from OrderEntity o left join fetch o.items
            where o.userName = :userName and o.orderNumber = :orderNumber
        """)
    Optional<OrderEntity> findByUserNameAndOrderNumber(String userName, String orderNumber);

}
