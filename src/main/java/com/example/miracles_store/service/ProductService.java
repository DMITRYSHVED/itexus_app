package com.example.miracles_store.service;

import com.example.miracles_store.dto.ProductCreateEditDto;
import com.example.miracles_store.dto.ProductReadDto;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.exception.DuplicateEntityException;
import com.example.miracles_store.exception.NoSuchEntityException;
import com.example.miracles_store.mapper.ProductMapper;
import com.example.miracles_store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductTypeService productTypeService;

    public Optional<Product> getById(int id) {
        return productRepository.findById(id);
    }

    public List<ProductReadDto> getAll(Integer key, int size) {
        Page<Product> productsPage;
        if (key != null) {
            productsPage = productRepository.findByIdGreaterThanOrderByIdAsc(key, PageRequest.of(0, size));
        } else {
            productsPage = productRepository.findAll(PageRequest.of(0, size));
        }
        return productsPage.getContent().stream()
                .map(ProductMapper.INSTANCE::toReadDto)
                .toList();
    }

    @Transactional
    public ProductReadDto create(ProductCreateEditDto productCreateEditDto) {
        Optional<ProductType> saveProductType = productTypeService.getById(productCreateEditDto.getProductTypeId());

        if (productRepository.findByName(productCreateEditDto.getName()).isPresent()) {
            throw new DuplicateEntityException("Product with name '" +
                    productCreateEditDto.getName() + "' already exists.");
        }
        if (saveProductType.isPresent()) {
            Product saveProduct = ProductMapper.INSTANCE.toProduct(productCreateEditDto);
            saveProduct.setProductType(saveProductType.get());
            productRepository.save(saveProduct);
            return ProductMapper.INSTANCE.toReadDto(saveProduct);
        } else {
            throw new NoSuchEntityException("The product type with the specified identifier was not found.");
        }
    }


    @Transactional
    public Optional<Product> update(int id, ProductCreateEditDto productCreateEditDto) {
        Optional<Product> optionalProduct;
        Optional<ProductType> optionalProductType;

        if (productRepository.findByName(productCreateEditDto.getName()).isPresent()) {
            throw new DuplicateEntityException("Product with name '" +
                    productCreateEditDto.getName() + "' already exists.");
        }
        optionalProduct = productRepository.findById(id);
        optionalProductType = productTypeService.getById(productCreateEditDto.getProductTypeId());
        if (optionalProduct.isPresent() && optionalProductType.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productCreateEditDto.getName());
            product.setCost(productCreateEditDto.getCost());
            product.setDescription(productCreateEditDto.getDescription());
            product.setProductType(optionalProductType.get());
            Product updatedProduct = productRepository.saveAndFlush(product);
            return Optional.of(updatedProduct);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public boolean deleteById(int id) {
        return productRepository.findById(id).map(product -> {
                    productRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    public List<ProductReadDto> getProductsByProductType(ProductType productType) {
        return productRepository.findByProductType(productType).stream().
                map(ProductMapper.INSTANCE::toReadDto).toList();
    }
}
