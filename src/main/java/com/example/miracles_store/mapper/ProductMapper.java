package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.ProductCreateEditDto;
import com.example.miracles_store.dto.ProductReadDto;
import com.example.miracles_store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "product.productType.name", target = "productTypeName")
    ProductReadDto toReadDto(Product product);

    @Mapping(source = "productTypeId", target = "productType.id")
    Product toProduct(ProductCreateEditDto productCreateEditDto);
}



