package com.example.miracles_store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "position_order")
@IdClass(PositionOrderId.class)
public class PositionOrder {

    @Id
    @ManyToOne
    @JoinColumn(name = "sell_position_id")
    private SellPosition sellPosition;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;
}
