package com.example.miracles_store.service;

import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.exception.ReferencedEntityException;
import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;

    public ProductType getById(Integer id) {
        return productTypeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find productType with id: " + id));
    }

    public Page<ProductType> getAll(Pageable pageable) {
        return productTypeRepository.findAll(pageable);
    }

    public ProductType create(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    public ProductType update(ProductType productType) {
        ProductType updateProductType = getById(productType.getId());

        updateProductType.setName(productType.getName());
        return updateProductType;
    }

    public void deleteById(Integer id) {
        ProductType productType = getById(id);

        if (productRepository.existsByProductType(productType)) {
            throw new ReferencedEntityException("Can't delete type '" +
                    productType.getName() + "' due to existing products of this type");
        }
        productTypeRepository.deleteById(id);
    }
}
