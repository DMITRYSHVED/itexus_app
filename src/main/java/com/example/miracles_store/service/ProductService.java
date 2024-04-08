package com.example.miracles_store.service;

import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.repository.QueryProductRepository;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final QueryProductRepository queryProductRepository;

    private final ProductTypeService productTypeService;

    public Product getById(Integer id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find product with id: " + id));
    }

    public Page<Product> getAll(Integer key, String type, Pageable pageable) {
        Page<Product> productsPage;

        if (key != null) {
            productsPage = queryProductRepository.findAllPage(key, pageable);
        } else {
            if (type != null) {
                productsPage = productRepository.findByProductTypeName(type, pageable);
            } else {
                productsPage = productRepository.findAll(pageable);
            }
        }
        return productsPage;
    }

    public Product create(Product product) {
        ProductType saveProductType = productTypeService.getById(product.getProductType().getId());

        product.setProductType(saveProductType);
        return productRepository.save(product);
    }


    public Product update(Product product) {
        Product updateProduct = getById(product.getId());
        ProductType productType = productTypeService.getById(product.getProductType().getId());

        updateProduct.setName(product.getName());
        updateProduct.setCost(product.getCost());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setProductType(productType);
        return productRepository.saveAndFlush(updateProduct);
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
