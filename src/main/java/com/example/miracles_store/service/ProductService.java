package com.example.miracles_store.service;

import com.example.miracles_store.dto.ProductFilter;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.QProduct;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.ProductRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product getById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Can't find product with id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<Product> getAll(ProductFilter filter, Pageable pageable) {
        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.name() != null) {
            predicate.and(qProduct.name.containsIgnoreCase(filter.name()));
        }
        if (filter.cost() != null) {
            predicate.and(qProduct.cost.eq(filter.cost()));
        }
        if (filter.productTypeId() != null) {
            predicate.and(qProduct.productType.id.eq(filter.productTypeId()));
        }
        return productRepository.findAll(predicate, pageable);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Product product) {
        return productRepository.saveAndFlush(product);
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}
