package com.example.miracles_store.controller;

import com.example.miracles_store.dto.AddressRequestDto;
import com.example.miracles_store.dto.AddressResponseDto;
import com.example.miracles_store.dto.filter.AddressFilter;
import com.example.miracles_store.mapper.AddressMapper;
import com.example.miracles_store.service.AddressService;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "address")
@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    private final AddressMapper addressMapper;

    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getAll(AddressFilter addressFilter,
                                                           @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        List<AddressResponseDto> response = addressService.getAll(addressFilter, pageable)
                .getContent().stream().map(addressMapper::toResponseDto).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDto> getById(@PathVariable("id") Integer id) {
        AddressResponseDto response = addressMapper.toResponseDto(addressService.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> create(@RequestBody @Validated({Default.class, CreateAction.class})
                                                     AddressRequestDto addressDto) {
        AddressResponseDto response = addressMapper.toResponseDto(addressService
                .save(addressMapper.requestDtoToEntity(addressDto)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<AddressResponseDto> update(@RequestBody @Validated({Default.class, UpdateAction.class})
                                                     AddressRequestDto addressDto) {
        AddressResponseDto response = addressMapper.toResponseDto(addressService
                .update(addressMapper.requestDtoToEntity(addressDto)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        addressService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
