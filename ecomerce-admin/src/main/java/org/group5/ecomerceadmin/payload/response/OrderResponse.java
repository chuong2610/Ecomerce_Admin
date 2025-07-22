package org.group5.ecomerceadmin.payload.response;

import lombok.Data;
import org.group5.ecomerceadmin.entity.Order;
import org.group5.ecomerceadmin.entity.Account;
import org.group5.ecomerceadmin.entity.ProductOrder;
import org.group5.ecomerceadmin.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private double totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Account account;
    private List<ProductOrder> items;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.account = order.getAccount();
        this.items = order.getProductOrders();
    }
}
