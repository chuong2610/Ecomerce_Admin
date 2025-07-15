package org.group5.ecomerceadmin.payload.response;

import lombok.Data;
import org.group5.ecomerceadmin.entity.ProductOrder;

@Data
public class ProductOrderResponse {
    private Long id;
    private String productName;
    private int quantity;
    private double price;

    public ProductOrderResponse(ProductOrder productOrder) {
        this.id = productOrder.getId();
        this.productName = productOrder.getProduct().getName();
        this.quantity = productOrder.getQuantity();
        this.price = productOrder.getPrice();
    }
}
