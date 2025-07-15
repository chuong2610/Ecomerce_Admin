package org.group5.ecomerceadmin.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductCreateDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String file;
    private String brandId;
    private String categoryId;
}
