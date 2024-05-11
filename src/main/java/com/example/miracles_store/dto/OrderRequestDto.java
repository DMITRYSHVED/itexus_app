package com.example.miracles_store.dto;

import com.example.miracles_store.entity.enums.OrderStatus;
import com.example.miracles_store.entity.order.SellPositionQuantity;
import com.example.miracles_store.validator.annotation.AddressIdExists;
import com.example.miracles_store.validator.annotation.CartItemsAreActive;
import com.example.miracles_store.validator.annotation.CartItemsInStock;
import com.example.miracles_store.validator.annotation.CorrectCartSum;
import com.example.miracles_store.validator.annotation.OrderIdExists;
import com.example.miracles_store.validator.annotation.PhoneFormat;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CorrectCartSum(groups = CreateAction.class)
public class OrderRequestDto {

    @Null(groups = CreateAction.class)
    @OrderIdExists(groups = UpdateAction.class)
    private Integer id;

    @PhoneFormat
    private String phone;

    private String orderComment;

    @NotNull(groups = CreateAction.class)
    @Null(groups = UpdateAction.class)
    private BigDecimal sum;

    @NotNull
    private OrderStatus orderStatus;

    @AddressIdExists
    private Integer addressId;

    @Null(groups = UpdateAction.class)
    @CartItemsInStock(groups = CreateAction.class)
    @CartItemsAreActive(groups = CreateAction.class)
    private Set<SellPositionQuantity> sellPositionQuantitySet;
}
