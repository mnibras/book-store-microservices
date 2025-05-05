package com.bookstore.notification.events;

import com.bookstore.notification.domain.NotificationService;
import com.bookstore.notification.domain.model.OrderCancelledEvent;
import com.bookstore.notification.domain.model.OrderCreatedEvent;
import com.bookstore.notification.domain.model.OrderDeliveredEvent;
import com.bookstore.notification.domain.model.OrderErrorEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    private final NotificationService notificationService;

    public OrderEventHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "${notification.new-orders-queue}")
    public void handle(OrderCreatedEvent event) {
        System.out.println("Order created event: " + event);
        notificationService.sendOrderCreatedNotification(event);
    }

    @RabbitListener(queues = "${notification.delivered-orders-queue}")
    public void handle(OrderDeliveredEvent event) {
        System.out.println("Order created event: " + event);
        notificationService.sendOrderDeliveredNotification(event);
    }

    @RabbitListener(queues = "${notification.cancelled-orders-queue}")
    public void handle(OrderCancelledEvent event) {
        System.out.println("Order created event: " + event);
        notificationService.sendOrderCancelledNotification(event);
    }

    @RabbitListener(queues = "${notification.error-orders-queue}")
    public void handle(OrderErrorEvent event) {
        System.out.println("Order created event: " + event);
        notificationService.sendOrderErrorEventNotification(event);
    }

}
