package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.ProductResponseDto;
import com.example.miracles_store.dto.SellPositionRequestDto;
import com.example.miracles_store.dto.SellPositionResponseDto;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.ProductRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class SellPositionMapper {

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected ProductMapper productMapper;

    @Mapping(target = "product", source = "sellPosition.product", qualifiedByName = "fromProductToProductDto")
    public abstract SellPositionResponseDto toResponseDto(SellPosition sellPosition);

    @Mapping(target = "product", source = "productId")
    public abstract SellPosition requestDtoToSellPosition(SellPositionRequestDto sellPositionRequestDto);

    @Named("fromProductToProductDto")
    protected ProductResponseDto fromProductToProductDto(Product product) {
        return productMapper.toResponseDto(product);
    }

    protected Product fromProductIdToProduct(Integer productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new ObjectNotFoundException("Can't find product with id " + productId));
    }
}
