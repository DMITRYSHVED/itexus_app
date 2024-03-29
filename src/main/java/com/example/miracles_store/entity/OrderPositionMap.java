package com.example.miracles_store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_position_map")
public class OrderPositionMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sell_position_id")
    private SellPosition sellPosition;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
