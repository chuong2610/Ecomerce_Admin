package org.group5.ecomerceadmin.payload.request;

import lombok.Data;
import org.group5.ecomerceadmin.enums.OrderStatus;

@Data
public class OrderStatusUpdateRequest {

    private OrderStatus status;

}
