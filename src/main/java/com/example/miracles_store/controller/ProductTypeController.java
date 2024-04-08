package com.example.miracles_store.controller;

import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.mapper.ProductTypeMapper;
import com.example.miracles_store.service.ProductTypeService;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productTypes")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    private final ProductTypeMapper productTypeMapper;

    @GetMapping
    public ResponseEntity<List<ProductTypeDto>> getAll(@RequestParam(name = "key", required = false) Integer key,
                                                       @RequestParam(name = "size", required = false) Integer size,
                                                       Pageable pageable) {
        List<ProductTypeDto> response = productTypeService.getAll(key, pageable).getContent().stream()
                .map(productTypeMapper::toDto)
                .toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductTypeDto> getById(@PathVariable("id") Integer id) {
        ProductTypeDto response = productTypeMapper.toDto(productTypeService.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductTypeDto> create(@RequestBody @Validated({Default.class, CreateAction.class})
                                                     ProductTypeDto productTypeDto) {
        ProductTypeDto response = productTypeMapper.toDto(productTypeService.
                create(productTypeMapper.toProductType(productTypeDto)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductTypeDto> update(@RequestBody @Validated({Default.class, UpdateAction.class})
                                                     ProductTypeDto productTypeDto) {
        ProductTypeDto response = productTypeMapper.toDto(productTypeService.
                update(productTypeMapper.toProductType(productTypeDto)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        String response = "Product deleted";
        productTypeService.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
