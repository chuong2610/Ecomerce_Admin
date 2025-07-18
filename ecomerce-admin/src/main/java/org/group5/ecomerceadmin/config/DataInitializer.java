package org.group5.ecomerceadmin.config;

import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.entity.Brand;
import org.group5.ecomerceadmin.entity.Category;
import org.group5.ecomerceadmin.entity.Product;
import org.group5.ecomerceadmin.enums.Role;
import org.group5.ecomerceadmin.repository.AccountRepository;
import org.group5.ecomerceadmin.repository.BrandRepository;
import org.group5.ecomerceadmin.repository.CategoryRepository;
import org.group5.ecomerceadmin.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataInitializer(AccountRepository accountRepository,
                           BrandRepository brandRepository,
                           CategoryRepository categoryRepository,
                           ProductRepository productRepository) {
        this.accountRepository = accountRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initAccounts();
        initBrands();
        initCategories();
        initProducts();
    }

    private void initAccounts() {
        if (accountRepository.count() == 0) {
            Account admin = new Account();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setRole(Role.ADMIN);
            admin.setFullName("Admin User"); // Thêm dòng này
            accountRepository.save(admin);

            Account customer = new Account();
            customer.setUsername("customer");
            customer.setPassword("123");
            customer.setRole(Role.CUSTOMER);
            customer.setFullName("Customer User"); // Thêm dòng này
            accountRepository.save(customer);

            System.out.println("Accounts initialized.");
        }
    }


    private void initBrands() {
        if (brandRepository.count() == 0) {
            List<Brand> brands = List.of(
                    new Brand("B1", "Apple", "Famous American brand"),
                    new Brand("B2", "Samsung", "Famous Korean brand"),
                    new Brand("B3", "Sony", "Famous Japanese brand"),
                    new Brand("B4", "Asus", "Famous Taiwanese brand"),
                    new Brand("B5", "Dell", "Famous American brand")
            );
            brandRepository.saveAll(brands);
            System.out.println("✔ Brands initialized.");
        }
    }

    private void initCategories() {
        if (categoryRepository.count() == 0) {
            List<Category> categories = List.of(
                    new Category("C1", "Laptop", "High-tech laptops", true),
                    new Category("C2", "Smartphone", "Smart mobile phones", true),
                    new Category("C3", "Tablet", "Tablets for work and entertainment", true),
                    new Category("C4", "Headphone", "All kinds of headphones", true),
                    new Category("C5", "Smartwatch", "Wearable smart watches", false)
            );
            categoryRepository.saveAll(categories);
            System.out.println("✔ Categories initialized.");
        }
    }

    private void initProducts() {
        if (productRepository.count() == 0) {
            List<Brand> brands = brandRepository.findAll();
            List<Category> categories = categoryRepository.findAll();

            List<Product> products = List.of(
                    new Product("P1", "MacBook Pro", 2500.0, 10, "High-end Apple laptop","1.jpg", brands.get(0), categories.get(0),true),
                    new Product("P2", "iPhone 15", 1200.0, 15, "Newest Apple smartphone","2.jpg", brands.get(0), categories.get(1),true),
                    new Product("P3", "Galaxy S23", 1100.0, 20, "Samsung flagship smartphone","3.jpg", brands.get(1), categories.get(1),true),
                    new Product("P4", "Galaxy Tab S8", 900.0, 12, "High-end Samsung tablet","4.jpg", brands.get(1), categories.get(2), true),
                    new Product("P5", "Sony WH-1000XM5", 400.0, 25, "Sony noise-canceling headphones","5.jpg", brands.get(2), categories.get(3), true),
                    new Product("P6", "Asus ROG Laptop", 2000.0, 8, "Powerful gaming laptop","6.jpg", brands.get(3), categories.get(0), true),
                    new Product("P7", "Dell XPS 13", 1800.0, 9, "Ultra-thin lightweight laptop","7.jpg", brands.get(4), categories.get(0), true),
                    new Product("P8", "Apple Watch Ultra", 800.0, 18, "Premium Apple smartwatch","8.jpg", brands.get(0), categories.get(4), true),
                    new Product("P9", "Samsung Galaxy Watch 6", 600.0, 14, "Latest Samsung smartwatch","9.jpg", brands.get(1), categories.get(4), true),
                    new Product("P10", "iPad Pro", 1300.0, 11, "Powerful Apple tablet","10.jpg", brands.get(0), categories.get(2), true)
            );

            productRepository.saveAll(products);
            System.out.println("✔ Products initialized.");
        }
    }

}

