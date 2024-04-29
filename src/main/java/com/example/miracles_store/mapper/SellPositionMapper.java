package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.ProductResponseDto;
import com.example.miracles_store.dto.SellPositionRequestDto;
import com.example.miracles_store.dto.SellPositionResponseDto;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.service.ProductService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class SellPositionMapper {

    @Autowired
    protected ProductService productService;

    @Autowired
    protected ProductMapper productMapper;

    @Mapping(target = "product", source = "sellPosition.product", qualifiedByName = "fromProductToProductDto")
    public abstract SellPositionResponseDto EntityToResponseDto(SellPosition sellPosition);

    @Mapping(target = "product", source = "productId")
    public abstract SellPosition requestDtoToEntity(SellPositionRequestDto sellPositionRequestDto);

    @Named("fromProductToProductDto")
    protected ProductResponseDto fromProductToProductDto(Product product) {
        return productMapper.toResponseDto(product);
    }

    protected Product fromProductIdToProduct(Integer productId) {
        return productService.getById(productId);
    }
}
