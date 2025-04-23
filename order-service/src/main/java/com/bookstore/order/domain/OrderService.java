package com.bookstore.order.domain;

import com.bookstore.order.domain.model.CreateOrderRequest;
import com.bookstore.order.domain.model.CreateOrderResponse;
import com.bookstore.order.domain.model.OrderCreatedEvent;
import com.bookstore.order.domain.model.OrderStatus;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderEventService orderEventService;
    private static final List<String> DELIVERY_ALLOWED_COUNTRIES = List.of("SRI LANKA", "USA", "GERMANY", "UK");

    OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderEventService orderEventService) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderEventService = orderEventService;
    }

    public CreateOrderResponse createOrder(String userName, CreateOrderRequest createOrderRequest) {
        orderValidator.validate(createOrderRequest);
        OrderEntity newOrder = OrderMapper.convertToEntity(createOrderRequest);
        newOrder.setUserName(userName);
        OrderEntity savedOrder = this.orderRepository.save(newOrder);
        log.info("Created Order with orderNumber: {}", savedOrder.getOrderNumber());
        OrderCreatedEvent orderCreatedEvent = OrderEventMapper.buildOrderCreatedEvent(savedOrder);
        orderEventService.save(orderCreatedEvent);
        return new CreateOrderResponse(savedOrder.getOrderNumber());
    }

    public void processNewOrders() {
        List<OrderEntity> newOrders = orderRepository.findByStatus(OrderStatus.NEW);
        log.info("Found {} new orders to process", newOrders.size());
        for (OrderEntity newOrder : newOrders) {
            this.process(newOrder);
        }
    }

    private void process(OrderEntity order) {
        try {
            if (canBeDelivered(order)) {
                log.info("OrderNumber: {} can be delivered", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.DELIVERED);
                orderEventService.save(OrderEventMapper.buildOrderDeliveredEvent(order));
            } else {
                log.info("OrderNumber: {} cannot be delivered", order.getOrderNumber());
                orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.CANCELLED);
                orderEventService.save(OrderEventMapper.buildOrderCancelledEvent(order, "Cannot deliver to the location"));
            }
        } catch (RuntimeException e) {
            log.error("Failed to process Order with orderNumber: {}", order.getOrderNumber(), e);
            orderRepository.updateOrderStatus(order.getOrderNumber(), OrderStatus.ERROR);
            orderEventService.save(OrderEventMapper.buildOrderErrorEvent(order, e.getMessage()));
        }
    }

    private boolean canBeDelivered(OrderEntity order) {
        return DELIVERY_ALLOWED_COUNTRIES.contains(order.getDeliveryAddress().country().toUpperCase());
    }
}
