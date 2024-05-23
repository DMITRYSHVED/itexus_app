package com.example.miracles_store.service;

import com.example.miracles_store.dto.filter.ProductFilter;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.QProduct;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.exception.ReferencedEntityException;
import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.repository.SellPositionRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductImageService productImageService;
    private final ProductRepository productRepository;

    private final SellPositionRepository sellPositionRepository;

    @Transactional(readOnly = true)
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

    public Product setProductImage(Integer productId, MultipartFile imageFile) throws IOException {
        Product product = getById(productId);
        String imageId = productImageService.addProductImage(imageFile);

        product.setImageId(imageId);
        return update(product);
    }

    public Product update(Product product) {
        return productRepository.saveAndFlush(product);
    }

    public Product deleteProductImage(Integer productId) {
        Product product = getById(productId);

        productImageService.deleteProductImage(product.getImageId());
        product.setImageId(null);
        return update(product);
    }

    public void deleteById(Integer id) {
        Product product = getById(id);

        if (sellPositionRepository.existsByProduct(product)) {
            throw new ReferencedEntityException(String
                    .format("Can't delete product '%s' due to existing products of this type", product.getName()));
        }
        productRepository.deleteById(id);
    }
}
