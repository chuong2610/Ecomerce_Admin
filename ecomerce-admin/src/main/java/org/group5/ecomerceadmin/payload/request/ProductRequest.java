package org.group5.ecomerceadmin.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductRequest {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private MultipartFile file;
    private String brandId;
    private String categoryId;
}
