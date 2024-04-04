package com.example.miracles_store.controller;

import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.mapper.ProductTypeMapper;
import com.example.miracles_store.service.ProductTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productTypes")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping
    public List<ProductTypeDto> getAll() {
        return productTypeService.getAll();
    }

    @GetMapping("/{id}")
    public ProductTypeDto getById(@PathVariable("id") int id) {
        ProductType productType = productTypeService.getById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ProductTypeMapper.INSTANCE.toDto(productType);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductTypeDto create(@RequestBody @Valid ProductTypeDto productTypeDto) {
        return productTypeService.create(productTypeDto);
    }

    @PutMapping("/{id}")
    public ProductTypeDto update(@PathVariable("id") int id,
                                 @RequestBody @Valid ProductTypeDto productTypeDto) {
        ProductType productType = productTypeService.update(id, productTypeDto).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ProductTypeMapper.INSTANCE.toDto(productType);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        if (!productTypeService.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
