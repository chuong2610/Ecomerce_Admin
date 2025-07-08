package org.group5.ecomerceadmin.service;

import jakarta.transaction.Transactional;
import org.group5.ecomerceadmin.entity.Category;
import org.group5.ecomerceadmin.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findByIsActiveTrue();
    }

    public Optional<Category> getById(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.filter(Category::isActive);
    }

    public Category create(Category category) {
        if (categoryRepository.existsById(category.getId())) {
            throw new IllegalArgumentException("Category with id " + category.getId() + " already exists.");
        }
        category.setActive(true);
        return categoryRepository.save(category);
    }

    public Category update(String id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));

        if (!existing.isActive()) {
            throw new IllegalArgumentException("Cannot update an inactive category with id: " + id);
        }

        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return categoryRepository.save(existing);
    }

    public void delete(String id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));

        if (!existing.isActive()) {
            throw new IllegalArgumentException("Category is already inactive.");
        }

        existing.setActive(false);
        categoryRepository.save(existing);
    }
}
