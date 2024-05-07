package com.example.miracles_store.dto;

import com.example.miracles_store.validator.annotation.ProductTypeIdExists;
import com.example.miracles_store.validator.annotation.UniqueProductTypeName;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@UniqueProductTypeName
public class ProductTypeDto {

    @ProductTypeIdExists(groups = UpdateAction.class)
    @Null(groups = CreateAction.class)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String name;
}
