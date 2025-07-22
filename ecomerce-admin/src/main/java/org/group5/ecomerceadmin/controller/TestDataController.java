//package org.group5.ecomerceadmin.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.group5.ecomerceadmin.entity.Account;
//import org.group5.ecomerceadmin.entity.Product;
//import org.group5.ecomerceadmin.enums.Role;
//import org.group5.ecomerceadmin.repository.AccountRepository;
//import org.group5.ecomerceadmin.repository.ProductRepository;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/test")
//public class TestDataController {
//
//    private final AccountRepository accountRepository;
//    private final ProductRepository productRepository;
//
//    @GetMapping("/create-sample-data")
//    public String createSampleData() {
//        try {
//            // Create sample customer if not exists
//            if (accountRepository.findByRole(Role.CUSTOMER).isEmpty()) {
//                Account customer = new Account();
//                customer.setUsername("customer1");
//                customer.setPassword("password123");
//                customer.setFullName("Test Customer");
//                customer.setRole(Role.CUSTOMER);
//                accountRepository.save(customer);
//                System.out.println("Created sample customer");
//            }
//
//            // Create sample product if not exists
//            if (productRepository.findAll().isEmpty()) {
//                Product product = new Product();
//                product.setId("PRODUCT001");
//                product.setName("Test Product");
//                product.setPrice(100.0);
//                product.setQuantity(50);
//                product.setDescription("Test product for order creation");
//                product.setActive(true);
//                productRepository.save(product);
//                System.out.println("Created sample product");
//            }
//
//            return "redirect:/orders/order-add";
//        } catch (Exception e) {
//            System.out.println("Error creating sample data: " + e.getMessage());
//            e.printStackTrace();
//            return "redirect:/orders/order-add";
//        }
//    }
//}
