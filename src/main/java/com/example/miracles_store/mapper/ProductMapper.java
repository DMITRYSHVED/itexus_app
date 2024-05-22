package com.example.miracles_store.mapper;

import com.example.miracles_store.constant.EmptyFieldConstant;
import com.example.miracles_store.dto.ProductRequestDto;
import com.example.miracles_store.dto.ProductResponseDto;
import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.service.ProductImageService;
import com.example.miracles_store.service.ProductTypeService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Base64;

@Mapper
public abstract class ProductMapper {

    @Autowired
    protected ProductTypeService productTypeService;

    @Autowired
    protected ProductTypeMapper productTypeMapper;

    @Autowired
    protected ProductImageService productImageService;

    @Mapping(target = "productType", source = "product.productType")
    @Mapping(target = "encodedImage", source = "product.imageId", qualifiedByName = "fromImageIdToEncodedImage")
    public abstract ProductResponseDto toResponseDto(Product product);

    @Mapping(target = "productType", source = "productTypeId")
    public abstract Product requestDtoToEntity(ProductRequestDto productCreateEditDto);

    protected ProductTypeDto fromTypeToTypeDto(ProductType productType) {
        return productTypeMapper.toDto(productType);
    }

    protected ProductType fromTypeIdToType(Integer productTypeId) {
        return productTypeService.getById(productTypeId);
    }

    @Named("fromImageIdToEncodedImage")
    protected String fromImageIdToEncodedImage(String imageId) throws IOException {
        if (imageId.equals(EmptyFieldConstant.NONE)) {
            return imageId;
        }
        return Base64.getEncoder().
                encodeToString(productImageService.getProductImage(imageId).getStream().readAllBytes());
    }
}



