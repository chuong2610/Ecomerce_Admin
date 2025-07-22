package org.group5.ecomerceadmin.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group5.ecomerceadmin.enums.OrderStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private Long accountId;
    private List<OrderItemRequest> items;
    private String statusString; // Use String for form binding

    // Manual getters/setters for compatibility
    public Long getAccountId() { return accountId; }
    public void setAccountId(Long accountId) { this.accountId = accountId; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }

    public String getStatusString() { return statusString; }
    public void setStatusString(String statusString) { this.statusString = statusString; }

    // Convenience method to get/set enum
    public OrderStatus getStatus() {
        if (statusString == null || statusString.trim().isEmpty()) {
            return OrderStatus.PENDING; // Default value
        }
        try {
            return OrderStatus.valueOf(statusString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OrderStatus.PENDING;
        }
    }

    public void setStatus(OrderStatus status) {
        this.statusString = status != null ? status.name() : null;
    }

    public void setStatus(String status) {
        this.statusString = status;
    }

}
