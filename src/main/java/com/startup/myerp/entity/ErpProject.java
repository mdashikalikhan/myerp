package com.startup.myerp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ERP_PROJECT")
@Data
public class ErpProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRJECT_ID", updatable = false)
    private Long id;
    @Column(name = "PRJECT_NAME", nullable = false)
    private String projectName;
    @Column(name = "PRJECT_DESCRIPTION", nullable = false)
    private String description;
    @Column(name = "PRJECT_Status", nullable = false)
    private Integer status;

}
