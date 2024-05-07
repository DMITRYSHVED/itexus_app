package com.example.miracles_store.controller;

import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.mapper.ProductTypeMapper;
import com.example.miracles_store.service.ProductTypeService;
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

@Tag(name = "productType")
@RestController
@RequestMapping("/api/v1/productTypes")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    private final ProductTypeMapper productTypeMapper;

    @GetMapping
    public ResponseEntity<List<ProductTypeDto>> getAll(@PageableDefault(size = 20, sort = "id") Pageable pageable) {
        List<ProductTypeDto> response = productTypeService.getAll(pageable).getContent().stream()
                .map(productTypeMapper::toDto).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeDto> getById(@PathVariable("id") Integer id) {
        ProductTypeDto response = productTypeMapper.toDto(productTypeService.getById(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductTypeDto> create(@RequestBody @Validated({Default.class, CreateAction.class})
                                                 ProductTypeDto productTypeDto) {
        ProductTypeDto response = productTypeMapper.toDto(productTypeService
                .save(productTypeMapper.toEntity(productTypeDto)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ProductTypeDto> update(@RequestBody @Validated({Default.class, UpdateAction.class})
                                                 ProductTypeDto productTypeDto) {
        ProductTypeDto response = productTypeMapper.toDto(productTypeService
                .update(productTypeMapper.toEntity(productTypeDto)));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        productTypeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
