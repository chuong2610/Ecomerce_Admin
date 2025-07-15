package org.group5.ecomerceadmin.controller;

import org.group5.ecomerceadmin.dto.ProductDTO;
import org.group5.ecomerceadmin.payload.request.ProductRequest;
import org.group5.ecomerceadmin.service.CategoryService;
import org.group5.ecomerceadmin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/products")
    public String findAll(@RequestParam(value = "search", required = false, defaultValue = "") String search, Model model) {

            if(search.equals("")) {
                model.addAttribute("products", productService.findAll());
            } else {
                List<ProductDTO> products = productService.search(search);
                model.addAttribute("products", products);
                model.addAttribute("search", search);
            }
            return "product-list";

    }
    @GetMapping("products/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/products/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new ProductRequest());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("formMode", "new");
        return "product-add";
    }

    @PostMapping("/products/save")
    public String save(@ModelAttribute("product") ProductRequest product, Model model) {
        model.addAttribute("product", product);
        productService.saveProduct(product);
        return "redirect:/products";
    }
    @GetMapping("/products/edit/{id}")
    public String update(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.findProductCreateDTOById(id));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("formMode", "new");
        return "product-add";
    }
    @GetMapping("/products/delete/{id}")
    public String delete(@PathVariable String id) {
        productService.delete(id);
        return "redirect:/products";
    }
    @GetMapping("/products/search/{keyword}")
    public ResponseEntity<?> search(@PathVariable String keyword) {
        try {
            return ResponseEntity.ok(productService.search(keyword));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/products/brand/{brandId}")
    public ResponseEntity<?> findByBrandId(@PathVariable String brandId) {
        try {
            return ResponseEntity.ok(productService.findByBrand(brandId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<?> findByCategoryId(@PathVariable String categoryId) {
        try {
            return ResponseEntity.ok(productService.findByCategory(categoryId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
