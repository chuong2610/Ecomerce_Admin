package org.group5.ecomerceadmin.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemRequest {

    private String productId;
    private int quantity;
}
