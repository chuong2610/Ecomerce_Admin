package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, String> {
    List<Brand> findByNameContainingIgnoreCase(String name);
}
