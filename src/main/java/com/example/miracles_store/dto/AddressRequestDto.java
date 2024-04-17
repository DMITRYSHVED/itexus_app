package com.example.miracles_store.dto;

import com.example.miracles_store.validator.annotation.AddressIdExists;
import com.example.miracles_store.validator.annotation.UserIdExists;
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
public class AddressRequestDto {

    @NotNull(groups = UpdateAction.class)
    @AddressIdExists(groups = UpdateAction.class)
    @Null(groups = CreateAction.class)
    private Integer id;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    @Size(max = 255)
    private String street;

    @NotBlank
    @Size(max = 50)
    private String house;

    @Size(max = 50)
    private String flat;

    @NotBlank
    @Size(max = 20)
    private String zipCode;

    @NotNull
    @UserIdExists
    private Integer userId;
}
