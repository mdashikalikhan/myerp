package com.startup.myerp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ERP_ORDER")
@Data
public class ErpOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", updatable = false)
    private Long id;
    @Column(name = "ORDER_NUMBER", unique = true, nullable = false)
    private String orderNumber;
    @Column(name = "ORDER_DATE", nullable = false)
    private LocalDate orderDate;
    @Column(name = "ORDER_AMOUNT", nullable = false)
    private BigDecimal totalAmount;

}
