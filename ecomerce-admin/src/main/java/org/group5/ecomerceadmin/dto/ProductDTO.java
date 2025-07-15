package org.group5.ecomerceadmin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String imageUrl;
    private String brand;
    private String category;

}
