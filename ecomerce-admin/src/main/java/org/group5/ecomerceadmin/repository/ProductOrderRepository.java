package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Order;
import org.group5.ecomerceadmin.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    List<ProductOrder> findByOrder_Id(Long orderId);
}
