package com.example.miracles_store.dto;

import com.example.miracles_store.validator.annotation.ItemInStock;
import com.example.miracles_store.validator.annotation.SellPositionIdExists;
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
@ItemInStock(groups = UpdateAction.class)
public class OrderCartRequestDto {

    @NotNull
    @UserIdExists
    private Integer userId;

    @NotNull
    @SellPositionIdExists
    private Integer sellPositionId;

    @NotNull(groups = UpdateAction.class)
    @Null(groups = CreateAction.class)
    @Null(groups = DeleteAction.class)
    @Positive(groups = UpdateAction.class)
    private Integer quantity;
}
