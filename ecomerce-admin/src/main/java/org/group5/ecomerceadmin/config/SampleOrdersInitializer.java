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
        if (orderRepository.count() == 0) {
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
        LocalDateTime now = LocalDateTime.now();
        
        // Create 20 sample orders spread across last 6 months
        for (int i = 0; i < 20; i++) {
            Account customer = customers.get(random.nextInt(customers.size()));
            
            // Random date within last 6 months
            LocalDateTime orderDate = now.minusDays(random.nextInt(180));
            
            // Random order status
            OrderStatus[] statuses = OrderStatus.values();
            OrderStatus status = statuses[random.nextInt(statuses.length)];
            
            // Create order
            org.group5.ecomerceadmin.entity.Order order = new org.group5.ecomerceadmin.entity.Order();
            order.setAccount(customer);
            order.setOrderDate(orderDate);
            order.setStatus(status);
            order.setActive(true);
            
            // Add 1-4 random products to order
            int productCount = 1 + random.nextInt(4);
            List<ProductOrder> productOrders = new ArrayList<>();
            double totalPrice = 0;
            
            for (int j = 0; j < productCount; j++) {
                Product product = products.get(random.nextInt(products.size()));
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
        
        System.out.println("✔ Sample orders created successfully!");
    }
}
