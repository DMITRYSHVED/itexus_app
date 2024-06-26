package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.PasswordChangeDto;
import com.example.miracles_store.dto.auth.SignUpRequest;
import com.example.miracles_store.dto.UserRequestDto;
import com.example.miracles_store.dto.UserResponseDto;
import com.example.miracles_store.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserMapper {

    User signUpRequestDtoToEntity(SignUpRequest request);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    User userRequestDtoToUser(UserRequestDto userRequestDto, @MappingTarget User user);

    UserResponseDto entityToResponseDto(User user);

    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", source = "newPassword")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    User passwordChangeDtoToEntity(PasswordChangeDto passwordChangeDto, @MappingTarget User user);
}
