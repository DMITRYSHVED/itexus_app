package com.example.miracles_store.dto;

import com.example.miracles_store.validator.UniqueProductTypeName;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@UniqueProductTypeName
public class ProductTypeDto {

    @NotNull(groups = UpdateAction.class)
    @Null(groups = CreateAction.class)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String name;
}
