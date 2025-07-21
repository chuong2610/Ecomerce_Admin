package org.group5.ecomerceadmin.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DashboardStats {
    private BigDecimal todayEarnings;
    private BigDecimal monthlyEarnings;
    private BigDecimal yearlyEarnings;
    private BigDecimal totalEarnings;
    
    private int totalProducts;
    private int totalOrders;
    private int totalCustomers;
    private int activeProducts;
    
    private List<ProductDashboardDTO> recentProducts;
    private List<ProductDashboardDTO> bestSellingProducts;
    private List<OrderDashboardDTO> recentOrders;
    
    private List<MonthlyEarningsDTO> monthlyEarningsChart;
    private List<CategoryStatsDTO> categoryStats;
}
