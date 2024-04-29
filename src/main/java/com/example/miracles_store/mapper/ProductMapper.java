package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.ProductRequestDto;
import com.example.miracles_store.dto.ProductResponseDto;
import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.service.ProductTypeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class ProductMapper {

    @Autowired
    protected ProductTypeService productTypeService;

    @Autowired
    protected ProductTypeMapper productTypeMapper;

    @Mapping(target = "productType", source = "product.productType", qualifiedByName = "fromTypeToTypeDto")
    public abstract ProductResponseDto toResponseDto(Product product);

    @Mapping(target = "productType", source = "productTypeId")
    public abstract Product requestDtoToEntity(ProductRequestDto productCreateEditDto);

    @Named("fromTypeToTypeDto")
    protected ProductTypeDto fromTypeToTypeDto(ProductType productType) {
        return productTypeMapper.toDto(productType);
    }

    protected ProductType fromTypeIdToType(Integer productTypeId) {
        return productTypeService.getById(productTypeId);
    }
}



