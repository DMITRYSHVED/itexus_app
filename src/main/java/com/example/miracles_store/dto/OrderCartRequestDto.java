package com.example.miracles_store.dto;

import com.example.miracles_store.validator.annotation.ItemInStock;
import com.example.miracles_store.validator.annotation.SellPositionIdExists;
import com.example.miracles_store.validator.annotation.UniqueSellPositionInCart;
import com.example.miracles_store.validator.annotation.UserIdExists;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.DeleteAction;
import com.example.miracles_store.validator.group.UpdateAction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UniqueSellPositionInCart(groups = {CreateAction.class})
@ItemInStock(groups = {CreateAction.class, UpdateAction.class})
public class OrderCartRequestDto {

    @NotNull
    @UserIdExists
    private Integer userId;

    @NotNull
    @SellPositionIdExists
    private Integer sellPositionId;

    @NotNull(groups = {CreateAction.class, UpdateAction.class})
    @Null(groups = DeleteAction.class)
    @Positive(groups = {CreateAction.class, UpdateAction.class})
    private Integer quantity;
}
