package org.group5.ecomerceadmin.payload.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String username;
    private String role;
}
