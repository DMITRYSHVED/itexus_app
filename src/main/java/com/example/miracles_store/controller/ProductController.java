package com.example.miracles_store.controller;

import com.example.miracles_store.dto.ProductFilter;
import com.example.miracles_store.dto.ProductRequestDto;
import com.example.miracles_store.dto.ProductResponseDto;
import com.example.miracles_store.mapper.ProductMapper;
import com.example.miracles_store.service.ProductService;
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

@Tag(name = "product_controller")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAll(ProductFilter productFilter,
                                                           @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        List<ProductResponseDto> response = productService.getAll(productFilter, pageable).getContent().stream()
                .map(productMapper::toResponseDto).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable("id") Integer id) {
        ProductResponseDto response = productMapper.toResponseDto(productService.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Validated({Default.class, CreateAction.class})
                                                     ProductRequestDto product) {
        ProductResponseDto response = productMapper.toResponseDto(productService.
                save(productMapper.requestDtoToProduct(product)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductResponseDto> update(@RequestBody @Validated({Default.class, UpdateAction.class})
                                                     ProductRequestDto productCreateEditDto) {
        ProductResponseDto response = productMapper.toResponseDto(productService.
                update(productMapper.requestDtoToProduct(productCreateEditDto)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
