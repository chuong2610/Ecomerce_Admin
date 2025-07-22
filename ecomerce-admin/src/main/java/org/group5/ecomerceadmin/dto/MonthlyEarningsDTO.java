package org.group5.ecomerceadmin.dto;

import lombok.Data;

@Data
public class MonthlyEarningsDTO {
    private String month;
    private double earnings;
    private int orderCount;
    
    public MonthlyEarningsDTO(String month, double earnings, int orderCount) {
        this.month = month;
        this.earnings = earnings;
        this.orderCount = orderCount;
    }
    
    public MonthlyEarningsDTO() {}
}
