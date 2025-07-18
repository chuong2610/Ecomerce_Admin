package org.group5.ecomerceadmin.controller;

import jakarta.validation.Valid;
import org.group5.ecomerceadmin.dto.CategoryRequestDTO;
import org.group5.ecomerceadmin.entity.Category;
import org.group5.ecomerceadmin.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAllforAdmin(@RequestParam(value = "kw", required = false) String keyword, Model model) {
        List<Category> categories = categoryService.getAllforAdmin();
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("categories", categoryService.searchByNameForAdmin(keyword));
            return "category-list";
        } else {
            model.addAttribute("categories", categories);
            return "category-list";
        }
    }

//    @GetMapping("/customer")
//    public String getAllforCustomer(@RequestParam("kw") String keyword, Model model) {
//        List<Category> categories = categoryService.getAllforCustomer();
//        if (keyword != null && !keyword.isEmpty()) {
//            model.addAttribute("categories", categoryService.searchByNameForCustomer(keyword));
//            return "category-list";
//        } else {
//            model.addAttribute("categories", categories);
//            return "category-list";
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable String id) {
        return categoryService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public String create(@ModelAttribute("category") CategoryRequestDTO category) {
        System.out.println(category.getId());
        System.out.println(category.getName());
        System.out.println(category.getDescription());
        categoryService.create(category);
        return "redirect:/categories";
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable String id, @Valid @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.update(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
