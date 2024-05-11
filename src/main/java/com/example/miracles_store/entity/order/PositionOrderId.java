package com.example.miracles_store.entity.order;

import com.example.miracles_store.entity.SellPosition;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PositionOrderId implements Serializable {

    private Order order;

    private SellPosition sellPosition;
}
