package org.group5.ecomerceadmin.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String fullName;
    private String password;
    private String role;
}
