package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.entity.ProductType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductTypeMapper {

    ProductTypeMapper INSTANCE = Mappers.getMapper(ProductTypeMapper.class);

    ProductTypeDto toDto(ProductType productType);

    ProductType toProductType(ProductTypeDto productTypeDto);
}
