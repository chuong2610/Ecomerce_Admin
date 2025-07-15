package org.group5.ecomerceadmin.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.group5.ecomerceadmin.enums.OrderStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderRequest {

    private Long accountId;
    private List<OrderItemRequest> items;
    private OrderStatus status;

}
