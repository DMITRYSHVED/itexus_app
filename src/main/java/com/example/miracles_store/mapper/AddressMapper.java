package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.AddressRequestDto;
import com.example.miracles_store.dto.AddressResponseDto;
import com.example.miracles_store.entity.Address;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class AddressMapper {


    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserMapper userMapper;

    public abstract AddressResponseDto toResponseDto(Address address);

    @Mapping(target = "user", source = "userId")
    public abstract Address requestDtoToAddress(AddressRequestDto addressRequestDto);

    protected User fromUserIdToUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException("Can't find user with id " + userId));
    }
}
