package org.group5.ecomerceadmin.dto;

import lombok.Data;

@Data
public class ProductDashboardDTO {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String imageUrl;
    private String category;
    private String brand;
    private String status;
    private int totalSold;
    private double profit;
    
    public ProductDashboardDTO(String id, String name, double price, int quantity, 
                              String imageUrl, String category, String brand, boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.category = category;
        this.brand = brand;
        this.status = isActive ? "Active" : "Inactive";
        this.totalSold = 0;
        this.profit = 0.0;
    }
    
    public ProductDashboardDTO(String id, String name, double price, String imageUrl, 
                              String category, int totalSold, double profit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.totalSold = totalSold;
        this.profit = profit;
        this.status = "Active";
    }
    
    public ProductDashboardDTO() {}
}
