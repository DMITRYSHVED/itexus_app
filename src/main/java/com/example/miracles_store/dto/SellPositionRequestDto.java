package com.example.miracles_store.dto;

import com.example.miracles_store.validator.annotation.ProductIdExists;
import com.example.miracles_store.validator.annotation.SellPositionIdExists;
import com.example.miracles_store.validator.annotation.UniqueSellPosition;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UniqueSellPosition
public class SellPositionRequestDto {

    @SellPositionIdExists(groups = UpdateAction.class)
    @Null(groups = CreateAction.class)
    private Integer id;

    @ProductIdExists
    private Integer productId;

    @NotBlank
    @Size(max = 50)
    private String size;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @NotNull
    private Boolean isActive;
}
