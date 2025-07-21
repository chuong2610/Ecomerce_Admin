package org.group5.ecomerceadmin.config;

import org.group5.ecomerceadmin.entity.*;
import org.group5.ecomerceadmin.enums.OrderStatus;
import org.group5.ecomerceadmin.enums.Role;
import org.group5.ecomerceadmin.repository.*;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ProductOrderRepository productOrderRepository;

    public DataInitializer(AccountRepository accountRepository,
                           BrandRepository brandRepository,
                           CategoryRepository categoryRepository,
                           ProductRepository productRepository,
                           OrderRepository orderRepository,
                           ProductOrderRepository productOrderRepository) {
        this.accountRepository = accountRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        initAccounts();
        initBrands();
        initCategories();
        initProducts();
        initCustomers();
        initOrders();
    }

    private void initAccounts() {
        if (accountRepository.count() == 0) {
            Account admin = new Account();
            admin.setUsername("admin");
            admin.setPassword("123");
            admin.setRole(Role.ADMIN);
            admin.setFullName("Admin User");
            admin.setActive(true);
            accountRepository.save(admin);

            Account customer = new Account();
            customer.setUsername("customer");
            customer.setPassword("123");
            customer.setRole(Role.CUSTOMER);
            customer.setFullName("Customer User");
            customer.setActive(true);
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

    private void initCustomers() {
        // Tạo thêm customer accounts nếu chưa có đủ
        long customerCount = accountRepository.countByRole(Role.CUSTOMER);
        if (customerCount < 10) {
            List<Account> customers = List.of(
                    createCustomer("john_doe", "John Doe"),
                    createCustomer("jane_smith", "Jane Smith"),
                    createCustomer("mike_wilson", "Mike Wilson"),
                    createCustomer("sarah_brown", "Sarah Brown"),
                    createCustomer("david_jones", "David Jones"),
                    createCustomer("emily_davis", "Emily Davis"),
                    createCustomer("chris_miller", "Chris Miller"),
                    createCustomer("lisa_taylor", "Lisa Taylor"),
                    createCustomer("robert_white", "Robert White"),
                    createCustomer("amanda_clark", "Amanda Clark")
            );
            accountRepository.saveAll(customers);
            System.out.println("✔ Customer accounts initialized.");
        }
    }

    private Account createCustomer(String username, String fullName) {
        Account customer = new Account();
        customer.setUsername(username);
        customer.setPassword("123456");
        customer.setRole(Role.CUSTOMER);
        customer.setFullName(fullName);
        customer.setActive(true);
        return customer;
    }

    private void initOrders() {
        if (orderRepository.count() == 0) {
            List<Account> customers = accountRepository.findByRole(Role.CUSTOMER);
            List<Product> products = productRepository.findAll();
            
            if (customers.isEmpty() || products.isEmpty()) {
                System.out.println("⚠ Cannot create orders: No customers or products found.");
                return;
            }
            
            Random random = new Random();

            // Tạo 20 orders mẫu
            for (int i = 1; i <= 20; i++) {
                Account randomCustomer = customers.get(random.nextInt(customers.size()));
                
                // Tạo ngày đặt hàng ngẫu nhiên trong 30 ngày qua
                LocalDateTime orderDate = LocalDateTime.now().minusDays(random.nextInt(30));
                
                // Chọn trạng thái ngẫu nhiên
                OrderStatus[] statuses = OrderStatus.values();
                OrderStatus randomStatus = statuses[random.nextInt(statuses.length)];
                
                // Chọn 1-3 sản phẩm ngẫu nhiên cho order này
                int numProducts = random.nextInt(3) + 1;
                double totalPrice = 0;
                
                Order order = new Order();
                order.setOrderDate(orderDate);
                order.setStatus(randomStatus);
                order.setAccount(randomCustomer);
                order.setActive(true);
                
                // Tính tổng tiền trước
                for (int j = 0; j < numProducts; j++) {
                    Product randomProduct = products.get(random.nextInt(products.size()));
                    int quantity = random.nextInt(3) + 1; // 1-3 items
                    totalPrice += randomProduct.getPrice() * quantity;
                }
                
                order.setTotalPrice(totalPrice);
                Order savedOrder = orderRepository.save(order);
                
                // Tạo ProductOrder items
                for (int j = 0; j < numProducts; j++) {
                    Product randomProduct = products.get(random.nextInt(products.size()));
                    int quantity = random.nextInt(3) + 1;
                    
                    ProductOrder productOrder = new ProductOrder();
                    productOrder.setOrder(savedOrder);
                    productOrder.setProduct(randomProduct);
                    productOrder.setQuantity(quantity);
                    productOrder.setPrice(randomProduct.getPrice());
                    
                    productOrderRepository.save(productOrder);
                }
            }
            System.out.println("✔ Sample orders initialized.");
        }
    }

}

