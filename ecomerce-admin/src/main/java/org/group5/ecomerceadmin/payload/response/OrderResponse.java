package org.group5.ecomerceadmin.payload.response;

import lombok.Data;
import org.group5.ecomerceadmin.entity.Order;
import org.group5.ecomerceadmin.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long orderId;
    private double totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;

    public OrderResponse(Order order) {
        this.orderId = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
    }
}
