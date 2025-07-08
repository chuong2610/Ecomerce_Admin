package org.group5.ecomerceadmin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @Column(name = "id")
    @NotBlank(message = "Id cannot be empty")
    private String id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String name;

    @Column(name = "product_price")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total price must be > 0")
    private double price;

    @Column(name = "product_quantity")
    @Min(value = 1, message = "Quantity must be >= 1")
    private int quantity;

    @Column(name = "product_description", columnDefinition = "NVARCHAR(255)")
    private String description;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders;

    public Product(String id, String name, double price, int quantity, String description, Brand brand, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.brand = brand;
        this.category = category;
    }

    public Product() {
    }
}
