package com.bookstore.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {

    boolean existsByEventId(String eventId);
    
}
