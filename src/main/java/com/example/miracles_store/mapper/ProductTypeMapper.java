package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.entity.ProductType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {

    ProductTypeDto toDto(ProductType productType);

    ProductType toProductType(ProductTypeDto productTypeDto);
}
