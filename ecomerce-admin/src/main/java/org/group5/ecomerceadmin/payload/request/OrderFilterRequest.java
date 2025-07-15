package org.group5.ecomerceadmin.payload.request;

import lombok.Data;
import org.group5.ecomerceadmin.enums.OrderStatus;

import java.time.LocalDate;

@Data
public class OrderFilterRequest {

    private Long accountId;
    private OrderStatus status;
    private LocalDate date;

}
