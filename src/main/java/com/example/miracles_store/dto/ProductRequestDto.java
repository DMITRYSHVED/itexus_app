package com.example.miracles_store.dto;

import com.example.miracles_store.validator.annotation.ProductIdExists;
import com.example.miracles_store.validator.annotation.ProductTypeIdExists;
import com.example.miracles_store.validator.annotation.UniqueProductName;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UniqueProductName
public class ProductRequestDto {

    @NotNull(groups = UpdateAction.class)
    @ProductIdExists(groups = UpdateAction.class)
    @Null(groups = CreateAction.class)
    private Integer id;

    @NotBlank
    @Size(max = 250)
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private BigDecimal cost;

    @NotNull
    @ProductTypeIdExists
    private Integer productTypeId;
}
