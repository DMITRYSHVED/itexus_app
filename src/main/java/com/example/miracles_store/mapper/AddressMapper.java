package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.AddressRequestDto;
import com.example.miracles_store.dto.AddressResponseDto;
import com.example.miracles_store.dto.UserResponseDto;
import com.example.miracles_store.entity.Address;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class AddressMapper {


    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected UserMapper userMapper;

    @Mapping(target = "user", source = "address.user", qualifiedByName = "fromUserToUserResponseDto")
    public abstract AddressResponseDto toResponseDto(Address address);

    @Mapping(target = "user", source = "userId")
    public abstract Address requestDtoToAddress(AddressRequestDto addressRequestDto);

    @Named("fromUserToUserResponseDto")
    protected UserResponseDto fromUserToUserResponseDto(User user) {
        return userMapper.userToUserResponseDto(user);
    }

    protected User fromUserIdToUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ObjectNotFoundException("Can't find user with id " + userId));
    }
}
