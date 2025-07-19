package org.group5.ecomerceadmin.service;

import jakarta.transaction.Transactional;
import org.group5.ecomerceadmin.dto.CategoryRequestDTO;
import org.group5.ecomerceadmin.dto.CategoryUpdateDTO;
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

    public List<Category> getAllforAdmin() {
        return categoryRepository.findAll();
    }

    public List<Category> getAllforCustomer() {
        return categoryRepository.findByIsActiveTrue();
    }

    public List<Category> searchByNameForAdmin(String name) {
        return categoryRepository.searchByNameContainingIgnoreCase(name);
    }

    public List<Category> searchByNameForCustomer(String name) {
        return categoryRepository.findByIsActiveTrueAndNameContainingIgnoreCase(name);
    }

    public Optional<Category> getById(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.filter(Category::isActive);
    }

    public Category create(CategoryRequestDTO categoryRequestDTO) {
        if (categoryRepository.existsById(categoryRequestDTO.getId())) {
            throw new IllegalArgumentException("Category already exists.");
        }
        Category category = new Category();
        category.setId(categoryRequestDTO.getId());
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
        category.setActive(true);

        return categoryRepository.save(category);
    }

    public Category update(String id, CategoryUpdateDTO category) {
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

    public void restore(String id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + id));
        if (existing.isActive()) {
            throw new IllegalArgumentException("Category is already active.");
        }
        existing.setActive(true);
        categoryRepository.save(existing);
    }
}
