package com.example.miracles_store.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sell_position")
public class SellPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String size;

    @Column
    private int quantity;

    @Column(name = "active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
