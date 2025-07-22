package org.group5.ecomerceadmin.service;

import lombok.RequiredArgsConstructor;
import org.group5.ecomerceadmin.dto.*;
import org.group5.ecomerceadmin.entity.Order;
import org.group5.ecomerceadmin.entity.Product;
import org.group5.ecomerceadmin.entity.ProductOrder;
import org.group5.ecomerceadmin.enums.Role;
import org.group5.ecomerceadmin.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final ProductOrderRepository productOrderRepository;

    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        
        // Calculate earnings
        calculateEarnings(stats);
        
        // Calculate counts
        calculateCounts(stats);
        
        // Get recent products
        stats.setRecentProducts(getRecentProducts());
        
        // Get best selling products
        stats.setBestSellingProducts(getBestSellingProducts());
        
        // Get recent orders
        stats.setRecentOrders(getRecentOrders());
        
        // Get monthly earnings for chart
        stats.setMonthlyEarningsChart(getMonthlyEarningsChart());
        
        // Get category statistics
        stats.setCategoryStats(getCategoryStats());
        
        return stats;
    }

    private void calculateEarnings(DashboardStats stats) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);
        
        LocalDateTime startOfMonth = today.withDayOfMonth(1).atStartOfDay();
        LocalDateTime startOfYear = today.withDayOfYear(1).atStartOfDay();
        
        // Today's earnings
        List<Order> todayOrders = orderRepository.findByIsActiveTrueAndOrderDateBetween(startOfDay, endOfDay);
        BigDecimal todayEarnings = todayOrders.stream()
            .map(order -> BigDecimal.valueOf(order.getTotalPrice()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setTodayEarnings(todayEarnings);
        
        // Monthly earnings
        List<Order> monthlyOrders = orderRepository.findByIsActiveTrueAndOrderDateAfter(startOfMonth);
        BigDecimal monthlyEarnings = monthlyOrders.stream()
            .map(order -> BigDecimal.valueOf(order.getTotalPrice()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setMonthlyEarnings(monthlyEarnings);
        
        // Yearly earnings
        List<Order> yearlyOrders = orderRepository.findByIsActiveTrueAndOrderDateAfter(startOfYear);
        BigDecimal yearlyEarnings = yearlyOrders.stream()
            .map(order -> BigDecimal.valueOf(order.getTotalPrice()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setYearlyEarnings(yearlyEarnings);
        
        // Total earnings
        List<Order> allOrders = orderRepository.findByIsActiveTrue();
        BigDecimal totalEarnings = allOrders.stream()
            .map(order -> BigDecimal.valueOf(order.getTotalPrice()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setTotalEarnings(totalEarnings);
    }

    private void calculateCounts(DashboardStats stats) {
        stats.setTotalProducts((int) productRepository.count());
        stats.setActiveProducts((int) productRepository.countByIsActiveTrue());
        stats.setTotalOrders((int) orderRepository.countByIsActiveTrue());
        stats.setTotalCustomers((int) accountRepository.countByRole(Role.CUSTOMER));
    }

    private List<ProductDashboardDTO> getRecentProducts() {
        List<Product> products = productRepository.findTop5ByIsActiveTrueOrderByIdDesc();
        return products.stream()
            .map(product -> new ProductDashboardDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                "http://localhost:8080/file/" + product.getImage(),
                product.getCategory().getName(),
                product.getBrand().getName(),
                product.isActive()
            ))
            .collect(Collectors.toList());
    }

    private List<ProductDashboardDTO> getBestSellingProducts() {
        List<ProductOrder> productOrders = productOrderRepository.findAll();
        
        Map<String, Integer> productSales = productOrders.stream()
            .collect(Collectors.groupingBy(
                po -> po.getProduct().getId(),
                Collectors.summingInt(ProductOrder::getQuantity)
            ));
        
        Map<String, Double> productProfits = productOrders.stream()
            .collect(Collectors.groupingBy(
                po -> po.getProduct().getId(),
                Collectors.summingDouble(po -> po.getPrice() * 0.2) // 20% profit margin
            ));
        
        return productSales.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .map(entry -> {
                Product product = productRepository.findById(entry.getKey()).orElse(null);
                if (product == null) return null;
                
                return new ProductDashboardDTO(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    "http://localhost:8080/file/" + product.getImage(),
                    product.getCategory().getName(),
                    entry.getValue(),
                    productProfits.getOrDefault(entry.getKey(), 0.0)
                );
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private List<OrderDashboardDTO> getRecentOrders() {
        List<Order> orders = orderRepository.findTop10ByIsActiveTrueOrderByOrderDateDesc();
        return orders.stream()
            .map(order -> new OrderDashboardDTO(
                order.getId(),
                order.getAccount().getFullName(),
                order.getTotalPrice(),
                order.getOrderDate(),
                order.getStatus(),
                order.getProductOrders() != null ? order.getProductOrders().size() : 0
            ))
            .collect(Collectors.toList());
    }

    private List<MonthlyEarningsDTO> getMonthlyEarningsChart() {
        LocalDate now = LocalDate.now();
        List<MonthlyEarningsDTO> monthlyData = new ArrayList<>();
        
        for (int i = 11; i >= 0; i--) {
            LocalDate targetMonth = now.minusMonths(i);
            LocalDateTime startOfMonth = targetMonth.withDayOfMonth(1).atStartOfDay();
            LocalDateTime endOfMonth = targetMonth.withDayOfMonth(targetMonth.lengthOfMonth()).atTime(23, 59, 59);
            
            List<Order> monthOrders = orderRepository.findByIsActiveTrueAndOrderDateBetween(startOfMonth, endOfMonth);
            double earnings = monthOrders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
            
            monthlyData.add(new MonthlyEarningsDTO(
                targetMonth.format(DateTimeFormatter.ofPattern("MMM yyyy")),
                earnings,
                monthOrders.size()
            ));
        }
        
        return monthlyData;
    }

    private List<CategoryStatsDTO> getCategoryStats() {
        return categoryRepository.findAll().stream()
            .filter(category -> category.isActive())
            .map(category -> {
                int productCount = (int) productRepository.countByCategory_IdAndIsActiveTrue(category.getId());
                
                List<ProductOrder> categoryOrders = productOrderRepository.findAll().stream()
                    .filter(po -> po.getProduct().getCategory().getId().equals(category.getId()))
                    .collect(Collectors.toList());
                
                double totalRevenue = categoryOrders.stream()
                    .mapToDouble(ProductOrder::getPrice)
                    .sum();
                
                int orderCount = (int) categoryOrders.stream()
                    .map(po -> po.getOrder().getId())
                    .distinct()
                    .count();
                
                return new CategoryStatsDTO(
                    category.getId(),
                    category.getName(),
                    productCount,
                    totalRevenue,
                    orderCount
                );
            })
            .collect(Collectors.toList());
    }
}
