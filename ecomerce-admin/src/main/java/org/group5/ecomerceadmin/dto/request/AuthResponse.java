package org.group5.ecomerceadmin.dto.request;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String username;
    private String role;
}
