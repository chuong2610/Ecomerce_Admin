package org.group5.ecomerceadmin.dto;

import lombok.Data;
import org.group5.ecomerceadmin.enums.OrderStatus;
import java.time.LocalDateTime;

@Data
public class OrderDashboardDTO {
    private Long orderId;
    private String customerName;
    private double totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private int itemCount;
    
    public OrderDashboardDTO(Long orderId, String customerName, double totalPrice, 
                           LocalDateTime orderDate, OrderStatus status, int itemCount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.itemCount = itemCount;
    }
    
    public OrderDashboardDTO() {}
}
