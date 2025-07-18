package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByIsActiveTrueAndNameContainingIgnoreCase(String name);
    List<Category> searchByNameContainingIgnoreCase(String name);
    List<Category> findByIsActiveTrue();
}
