package com.bookstore.order.domain;

import com.bookstore.order.domain.model.CreateOrderRequest;
import com.bookstore.order.domain.model.OrderDTO;
import com.bookstore.order.domain.model.OrderItem;
import com.bookstore.order.domain.model.OrderStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    static OrderDTO convertToDTO(OrderEntity order) {
        Set<OrderItem> orderItems = order.getItems().stream()
                .map(item -> new OrderItem(item.getCode(), item.getName(), item.getPrice(), item.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderDTO(
                order.getOrderNumber(),
                order.getUserName(),
                orderItems,
                order.getCustomer(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getComments(),
                order.getCreatedAt());
    }

}
