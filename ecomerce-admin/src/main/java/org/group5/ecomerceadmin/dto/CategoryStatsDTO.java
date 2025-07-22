package org.group5.ecomerceadmin.dto;

import lombok.Data;

@Data
public class CategoryStatsDTO {
    private String categoryId;
    private String categoryName;
    private int productCount;
    private double totalRevenue;
    private int orderCount;
    
    public CategoryStatsDTO(String categoryId, String categoryName, int productCount, 
                           double totalRevenue, int orderCount) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productCount = productCount;
        this.totalRevenue = totalRevenue;
        this.orderCount = orderCount;
    }
    
    public CategoryStatsDTO() {}
}
