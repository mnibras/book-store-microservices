package com.bookstore.order.domain;

import com.bookstore.order.domain.model.CreateOrderRequest;
import com.bookstore.order.domain.model.OrderItem;
import com.bookstore.order.domain.model.OrderStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class OrderMapper {

    private OrderMapper() {

    }

    static OrderEntity convertToEntity(CreateOrderRequest createOrderRequest) {
        OrderEntity newOrder = new OrderEntity();
        Set<OrderItemEntity> orderItems = new HashSet<>();

        newOrder.setOrderNumber(UUID.randomUUID().toString());
        newOrder.setStatus(OrderStatus.NEW);
        newOrder.setCustomer(createOrderRequest.customer());
        newOrder.setDeliveryAddress(createOrderRequest.deliveryAddress());

        for (OrderItem item : createOrderRequest.items()) {
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setCode(item.code());
            orderItem.setName(item.name());
            orderItem.setPrice(item.price());
            orderItem.setQuantity(item.quantity());
            orderItem.setOrder(newOrder);
            orderItems.add(orderItem);
        }

        newOrder.setItems(orderItems);
        return newOrder;
    }

}
