package org.group5.ecomerceadmin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.group5.ecomerceadmin.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be > 0")
    private double totalPrice;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders;

    public Order(double totalPrice, LocalDateTime orderDate, OrderStatus status, Account account) {
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
        this.account = account;
    }
    public Order() {
    }
}
