package org.group5.ecomerceadmin.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BrandUpdateRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String description;
}

