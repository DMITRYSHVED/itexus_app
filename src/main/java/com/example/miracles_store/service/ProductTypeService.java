package com.example.miracles_store.service;

import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.exception.ReferencedEntityException;
import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductType getById(Integer id) {
        return productTypeRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Can't find productType with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<ProductType> getAll(Pageable pageable) {
        return productTypeRepository.findAll(pageable);
    }

    public ProductType save(ProductType productType) {
        return productTypeRepository.save(productType);
    }

    public ProductType update(ProductType productType) {
        return productTypeRepository.saveAndFlush(productType);
    }

    public void deleteById(Integer id) {
        ProductType productType = getById(id);

        if (productRepository.existsByProductType(productType)) {
            throw new ReferencedEntityException(String
                    .format("Can't delete type '%s' due to existing products of this type", productType.getName()));
        }
        productTypeRepository.deleteById(id);
    }
}
