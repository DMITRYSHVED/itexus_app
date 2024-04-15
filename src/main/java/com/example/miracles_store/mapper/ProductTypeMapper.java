package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.entity.ProductType;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ProductTypeMapper {

    ProductTypeDto toDto(ProductType productType);

    ProductType toEntity(ProductTypeDto productTypeDto);
}
