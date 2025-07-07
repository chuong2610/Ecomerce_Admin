package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
