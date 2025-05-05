package com.bookstore.order.job;

import com.bookstore.order.domain.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
class NewOrderProcessingJob {

    private static final Logger log = LoggerFactory.getLogger(NewOrderProcessingJob.class);

    private final OrderService orderService;

    NewOrderProcessingJob(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(cron = "${orders.new-orders-job-cron}")
    public void processNewOrders() {
        log.info("Processing new orders at {}", Instant.now());
        orderService.processNewOrders();
    }
}
