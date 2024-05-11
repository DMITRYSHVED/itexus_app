package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.AddressRequestDto;
import com.example.miracles_store.dto.AddressResponseDto;
import com.example.miracles_store.entity.Address;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class AddressMapper {


    @Autowired
    protected UserService userService;

    @Autowired
    protected UserMapper userMapper;

    public abstract AddressResponseDto toResponseDto(Address address);

    @Mapping(target = "user", source = "userId")
    public abstract Address requestDtoToEntity(AddressRequestDto addressRequestDto);

    protected User fromUserIdToUser(Integer userId) {
        return userService.getById(userId);
    }
}
