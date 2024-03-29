package com.example.miracles_store.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "position_order")
@IdClass(PositionOrderPK.class)
public class PositionOrder {

    @Id
    @ManyToOne
    @JoinColumn(name = "sell_position_id")
    private SellPosition sellPosition;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column
    private int quantity;
}
