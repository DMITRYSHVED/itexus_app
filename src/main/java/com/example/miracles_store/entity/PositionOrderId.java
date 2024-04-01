package com.example.miracles_store.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class PositionOrderId implements Serializable {

    private Order order;

    private SellPosition sellPosition;
}
