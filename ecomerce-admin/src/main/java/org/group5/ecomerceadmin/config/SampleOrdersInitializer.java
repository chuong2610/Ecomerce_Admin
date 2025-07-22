package org.group5.ecomerceadmin.config;

import lombok.RequiredArgsConstructor;
import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.entity.Product;
import org.group5.ecomerceadmin.entity.ProductOrder;
import org.group5.ecomerceadmin.enums.OrderStatus;
import org.group5.ecomerceadmin.enums.Role;
import org.group5.ecomerceadmin.repository.AccountRepository;
import org.group5.ecomerceadmin.repository.OrderRepository;
import org.group5.ecomerceadmin.repository.ProductOrderRepository;
import org.group5.ecomerceadmin.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Order(2) // Run after DataInitializer
@RequiredArgsConstructor
public class SampleOrdersInitializer implements CommandLineRunner {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final ProductOrderRepository productOrderRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (orderRepository.count() < 200) { // Increase threshold for more historical data
            createSampleOrders();
        }
    }

    private void createSampleOrders() {
        List<Account> customers = accountRepository.findByRole(Role.CUSTOMER);
        List<Product> products = productRepository.findAll();
        
        if (customers.isEmpty() || products.isEmpty()) {
            System.out.println("No customers or products found for sample orders");
            return;
        }

        Random random = new Random();
        
        // Create orders for different years with realistic distribution
        createOrdersForYear(2023, 80, customers, products, random); // 80 orders for 2023
        createOrdersForYear(2024, 100, customers, products, random); // 100 orders for 2024
        createOrdersForYear(2025, 60, customers, products, random); // 60 orders for 2025 (current year)
        
        System.out.println("✔ Enhanced sample orders created successfully! Total orders: " + orderRepository.count());
    }
    
    private void createOrdersForYear(int year, int orderCount, List<Account> customers, List<Product> products, Random random) {
        for (int i = 0; i < orderCount; i++) {
            Account customer = customers.get(random.nextInt(customers.size()));
            
            // Random date within the specified year
            LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
            LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59);
            long daysBetween = java.time.Duration.between(startOfYear, endOfYear).toDays();
            LocalDateTime orderDate = startOfYear.plusDays(random.nextInt((int) daysBetween + 1))
                                                 .plusHours(random.nextInt(24))
                                                 .plusMinutes(random.nextInt(60));
            
            // Random order status with realistic distribution
            OrderStatus status;
            double statusRandom = random.nextDouble();
            if (year < 2025) {
                // Historical orders - mostly completed
                if (statusRandom < 0.85) {
                    status = OrderStatus.COMPLETED;
                } else if (statusRandom < 0.95) {
                    status = OrderStatus.CANCELLED;
                } else {
                    status = OrderStatus.PROCESSING;
                }
            } else {
                // Current year orders - mix of statuses
                if (statusRandom < 0.7) {
                    status = OrderStatus.COMPLETED;
                } else if (statusRandom < 0.85) {
                    status = OrderStatus.PENDING;
                } else if (statusRandom < 0.95) {
                    status = OrderStatus.PROCESSING;
                } else {
                    status = OrderStatus.CANCELLED;
                }
            }
            
            // Create order
            org.group5.ecomerceadmin.entity.Order order = new org.group5.ecomerceadmin.entity.Order();
            order.setAccount(customer);
            order.setOrderDate(orderDate);
            order.setStatus(status);
            order.setActive(true);
            
            // Add 1-5 random products to order with realistic distribution
            int productCount;
            double productCountRandom = random.nextDouble();
            if (productCountRandom < 0.4) {
                productCount = 1; // 40% single item
            } else if (productCountRandom < 0.7) {
                productCount = 2; // 30% two items
            } else if (productCountRandom < 0.9) {
                productCount = 3; // 20% three items
            } else {
                productCount = 4 + random.nextInt(2); // 10% four or five items
            }
            
            List<ProductOrder> productOrders = new ArrayList<>();
            double totalPrice = 0;
            
            // Use Set to avoid duplicate products in same order
            List<Product> availableProducts = new ArrayList<>(products);
            for (int j = 0; j < productCount && !availableProducts.isEmpty(); j++) {
                int productIndex = random.nextInt(availableProducts.size());
                Product product = availableProducts.remove(productIndex);
                
                int quantity = 1 + random.nextInt(3); // 1-3 items
                double itemPrice = product.getPrice() * quantity;
                
                ProductOrder po = new ProductOrder();
                po.setProduct(product);
                po.setOrder(order);
                po.setQuantity(quantity);
                po.setPrice(itemPrice);
                
                productOrders.add(po);
                totalPrice += itemPrice;
            }
            
            order.setTotalPrice(totalPrice);
            order.setProductOrders(productOrders);
            
            // Save order (cascade will save ProductOrders)
            orderRepository.save(order);
        }
    }
}
