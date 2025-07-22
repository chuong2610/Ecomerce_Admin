package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByBrand_IdAndIsActiveTrue(String brandId);
    List<Product> findByCategory_IdAndIsActiveTrue(String categoryId);
    
    // Dashboard queries
    long countByIsActiveTrue();
    long countByCategory_IdAndIsActiveTrue(String categoryId);
    List<Product> findTop5ByIsActiveTrueOrderByIdDesc();
}
