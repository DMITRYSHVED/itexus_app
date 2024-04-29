package com.example.miracles_store.dto;

import com.example.miracles_store.validator.annotation.AddressIdExists;
import com.example.miracles_store.validator.annotation.OrderIdExists;
import com.example.miracles_store.validator.annotation.PhoneFormat;
import com.example.miracles_store.validator.annotation.UserIdExists;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    @NotNull(groups = UpdateAction.class)
    @Null(groups = CreateAction.class)
    @OrderIdExists(groups = UpdateAction.class)
    private Integer id;

    @NotNull
    @PhoneFormat
    private String phone;

    private String orderComment;

    private BigDecimal sum;

    private String orderStatus;

    @NotNull
    @AddressIdExists
    private Integer addressId;

    @NotNull(groups = CreateAction.class)
    @Null(groups = UpdateAction.class)
    @UserIdExists
    private Integer userId;
}
