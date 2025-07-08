package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}
