package org.group5.ecomerceadmin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group5.ecomerceadmin.enums.Role;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    @Nationalized
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "full_name", nullable = false, length = 100)
    @Nationalized
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @Column(name = "status")
    private boolean active = true;

}
