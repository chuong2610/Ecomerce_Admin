package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Order;
import org.group5.ecomerceadmin.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByAccountIdAndIsActiveTrueAndStatusAndOrderDateBetween(Long accountId, OrderStatus status, LocalDateTime start, LocalDateTime end);

    List<Order> findByIsActiveTrue();
    
    // Dashboard queries
    List<Order> findByIsActiveTrueAndOrderDateBetween(LocalDateTime start, LocalDateTime end);
    List<Order> findByIsActiveTrueAndOrderDateAfter(LocalDateTime date);
    long countByIsActiveTrue();
    List<Order> findTop10ByIsActiveTrueOrderByOrderDateDesc();
}
