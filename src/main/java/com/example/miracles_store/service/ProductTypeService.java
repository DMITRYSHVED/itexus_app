package com.example.miracles_store.service;

import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.exception.DuplicateEntityException;
import com.example.miracles_store.exception.ReferencedEntityException;
import com.example.miracles_store.mapper.ProductTypeMapper;
import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;


    private final ProductRepository productRepository;

    public Optional<ProductType> getById(int id) {
        return productTypeRepository.findById(id);
    }

    public List<ProductTypeDto> getAll() {
        return productTypeRepository.findAll().stream().
                map(ProductTypeMapper.INSTANCE::toDto).toList();
    }

    @Transactional
    public ProductTypeDto create(ProductTypeDto productTypeDto) {
        ProductType saveProductType;

        if (productTypeRepository.findByName(productTypeDto.getName()).isPresent()) {
            throw new DuplicateEntityException("ProductType '" +
                    productTypeDto.getName() + "' already exists.");
        }
        saveProductType = ProductTypeMapper.INSTANCE.toProductType(productTypeDto);
        productTypeRepository.save(saveProductType);
        return ProductTypeMapper.INSTANCE.toDto(saveProductType);
    }

    @Transactional
    public Optional<ProductType> update(int id, ProductTypeDto productTypeDto) {
        Optional<ProductType> optionalProductType;

        if (productTypeRepository.findByName(productTypeDto.getName()).isPresent()) {
            throw new DuplicateEntityException("ProductType '" +
                    productTypeDto.getName() + "' already exists.");
        }
        optionalProductType = productTypeRepository.findById(id);
        if (optionalProductType.isPresent()) {
            ProductType productType = optionalProductType.get();
            productType.setName(productTypeDto.getName());
            return Optional.of(productType);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean deleteById(int id) {
        return productTypeRepository.findById(id).map(productType -> {
                    if (productRepository.findByProductType(productType).isEmpty()) {
                    throw new ReferencedEntityException("Can't delete type '" +
                            productType.getName() + "' due to existing products of the same type");
                    } else {
                        productTypeRepository.deleteById(id);
                        return true;
                    }
                })
                .orElse(false);
    }
}
