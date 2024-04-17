package com.example.miracles_store.controller;

import com.example.miracles_store.dto.UserRequestDto;
import com.example.miracles_store.dto.UserResponseDto;
import com.example.miracles_store.dto.filter.AddressFilter;
import com.example.miracles_store.dto.filter.UserFilter;
import com.example.miracles_store.mapper.UserMapper;
import com.example.miracles_store.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "user_controller")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll(UserFilter userFilter, AddressFilter addressFilter,
                                                        @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        List<UserResponseDto> response = (userService.getAll(userFilter, addressFilter, pageable).getContent()
                .stream().map(userMapper::userToUserResponseDto)).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Integer id) {
        UserResponseDto response = userMapper.userToUserResponseDto(userService.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponseDto> update(@RequestBody @Validated UserRequestDto userRequestDto) {
        UserResponseDto response = userMapper.userToUserResponseDto(userService.update(
                (userMapper.userRequestDtoToUser(userRequestDto, userService.getById(userRequestDto.getId())))));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}