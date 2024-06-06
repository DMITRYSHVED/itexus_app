package com.example.miracles_store.controller;

import com.example.miracles_store.dto.ProductRequestDto;
import com.example.miracles_store.dto.ProductResponseDto;
import com.example.miracles_store.dto.filter.ProductFilter;
import com.example.miracles_store.mapper.ProductMapper;
import com.example.miracles_store.service.ProductService;
import com.example.miracles_store.util.ContentTypeValidator;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Tag(name = "product")
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAll(ProductFilter productFilter,
                                                           @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        Page<ProductResponseDto> response = productService.getAll(productFilter, pageable).
                map(productMapper::toResponseDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable("id") Integer id) {
        ProductResponseDto response = productMapper.toResponseDto(productService.getById(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(@RequestBody @Validated({Default.class, CreateAction.class})
                                                     ProductRequestDto product) {
        ProductResponseDto response = productMapper.toResponseDto(productService.save(productMapper.
                requestDtoToEntity(product)));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ProductResponseDto> update(@RequestBody @Validated({Default.class, UpdateAction.class})
                                                     ProductRequestDto productRequestDto) {
        ProductResponseDto response = productMapper.toResponseDto(productService.
                update(productMapper.requestDtoToEntity(productRequestDto)));
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/{productId}", consumes = {"multipart/form-data"})
    public ResponseEntity<ProductResponseDto> setProductImage(@PathVariable("productId") Integer productId,
                                                              @RequestParam("image") MultipartFile image)
            throws IOException {
        if (!ContentTypeValidator.isImageContentType(image.getContentType())) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Invalid file type");
        }
        ProductResponseDto response = productMapper.toResponseDto(productService.setProductImage(productId, image));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteImage/{productId}")
    public ResponseEntity<ProductResponseDto> deleteProductImage(@PathVariable("productId") Integer productId) {
        ProductResponseDto response = productMapper.toResponseDto(productService.deleteProductImage(productId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
