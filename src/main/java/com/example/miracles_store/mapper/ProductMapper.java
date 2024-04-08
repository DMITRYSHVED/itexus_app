package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.ProductRequestDto;
import com.example.miracles_store.dto.ProductResponseDto;
import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.repository.ProductTypeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    protected ProductTypeRepository productTypeRepository;

    protected ProductTypeMapper productTypeMapper;

    @Autowired
    public void setProductTypeRepository(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @Autowired
    public void setProductTypeMapper(ProductTypeMapper productTypeMapper) {
        this.productTypeMapper = productTypeMapper;
    }

    @Mapping(target = "productType", source = "product.productType", qualifiedByName = "fromTypeToTypeDto")
    public abstract ProductResponseDto toResponseDto(Product product);

    @Mapping(source = "productTypeId", target = "productType", qualifiedByName = "fromTypeIdToType")
    public abstract Product requestDtoToProduct(ProductRequestDto productCreateEditDto);

    @Named("fromTypeToTypeDto")
    protected ProductTypeDto fromTypeToTypeDto(ProductType productType) {
        return productTypeMapper
                .toDto(productType);
    }

    @Named("fromTypeIdToType")
    protected ProductType fromTypeIdToType(Integer productTypeId) {
        return productTypeRepository.findById(productTypeId).orElse(null);
    }
}



