package com.startup.myerp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ERP_USER")
@Data
public class ErpUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role;
}
