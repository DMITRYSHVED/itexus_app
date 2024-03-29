package com.example.miracles_store.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class PositionOrderPK implements Serializable {

    private Order order;

    private SellPosition sellPosition;
}
