package org.group5.ecomerceadmin.repository;

import org.group5.ecomerceadmin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
