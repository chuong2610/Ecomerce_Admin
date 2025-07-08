package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
