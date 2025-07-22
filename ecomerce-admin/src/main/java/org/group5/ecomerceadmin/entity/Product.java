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
@ToString(exclude = {"productOrders"})
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

    @Column(name = "product_image", columnDefinition = "NVARCHAR(255)")
    private String image;

    @Column(name = "is_active")
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductOrder> productOrders;


    public Product(String id, String name, double price, int quantity, String description, String image, Brand brand, Category category, boolean isActive) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
        this.brand = brand;
        this.category = category;
        this.isActive = isActive;
    }

    public Product() {
    }

    // Manual getters/setters for compatibility
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public List<ProductOrder> getProductOrders() { return productOrders; }
    public void setProductOrders(List<ProductOrder> productOrders) { this.productOrders = productOrders; }
}
