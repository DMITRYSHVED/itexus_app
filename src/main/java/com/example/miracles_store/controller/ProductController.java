package com.example.miracles_store.controller;

import com.example.miracles_store.dto.ProductCreateEditDto;
import com.example.miracles_store.dto.ProductReadDto;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.mapper.ProductMapper;
import com.example.miracles_store.service.ProductService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductTypeService productTypeService;


    @GetMapping
    public List<ProductReadDto> getAll(@RequestParam(name = "key", required = false) Integer key,
                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        return productService.getAll(key, size);
    }


    @GetMapping("/{id}")
    public ProductReadDto getById(@PathVariable("id") int id) {
        Product product = productService.getById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ProductMapper.INSTANCE.toReadDto(product);
    }

    @GetMapping("/byProductType/{productTypeId}")
    public List<ProductReadDto> getByProductType(@PathVariable("productTypeId") int id) {
        ProductType productType = productTypeService.getById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return productService.getProductsByProductType(productType);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductReadDto create(@RequestBody @Valid ProductCreateEditDto productCreateEditDto) {
        return productService.create(productCreateEditDto);
    }

    @PutMapping("/{id}")
    public ProductReadDto update(@PathVariable("id") int id,
                                 @RequestBody @Valid ProductCreateEditDto productCreateEditDto) {
        Product product = productService.update(id, productCreateEditDto).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ProductMapper.INSTANCE.toReadDto(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        if (!productService.deleteById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
