package com.example.miracles_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressResponseDto {

    private Integer id;

    private String city;

    private String street;

    private String house;

    private String flat;

    private String zipCode;

    private UserResponseDto user;
}
