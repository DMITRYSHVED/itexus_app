package com.example.miracles_store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column
    private String description;

    @Column
    private String size;

    @Column
    private double cost;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;
}
