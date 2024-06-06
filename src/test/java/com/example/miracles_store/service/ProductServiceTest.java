package com.example.miracles_store.service;

import com.example.miracles_store.constant.ProductTestConstant;
import com.example.miracles_store.dto.filter.ProductFilter;
import com.example.miracles_store.entity.Product;
import com.example.miracles_store.exception.ReferencedEntityException;
import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.repository.SellPositionRepository;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductService.class)
public class ProductServiceTest {

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductImageService productImageService;

    @MockBean
    SellPositionRepository sellPositionRepository;

    @Autowired
    ProductService productService;

    Product sweaterProduct;

    Product airForceProduct;

    String imageId;

    @BeforeEach
    void setup() {
        sweaterProduct = new Product(ProductTestConstant.SWEATER_PRODUCT_ID, ProductTestConstant.PRODUCT_NAME_SWEATER,
                ProductTestConstant.PRODUCT_DESCRIPTION_SWEATER, ProductTestConstant.PRODUCT_COST_SWEATER,
                null, ProductTestConstant.PRODUCT_TYPE_SWEATER);

        airForceProduct = new Product(ProductTestConstant.AIR_FORCE_PRODUCT_ID, ProductTestConstant.PRODUCT_NAME_AIR_FORCE,
                ProductTestConstant.PRODUCT_DESCRIPTION_AIR_FORCE, ProductTestConstant.PRODUCT_COST_AIR_FORCE,
                null, ProductTestConstant.PRODUCT_TYPE_AIR_FORCE);

        imageId = "imageId";
    }

    @Test
    void getById() {
        Product expected = airForceProduct;
        doReturn(Optional.of(airForceProduct)).when(productRepository).findById(airForceProduct.getId());

        var actual = productService.getById(airForceProduct.getId());

        assertEquals(expected, actual);
        verify(productRepository).findById(airForceProduct.getId());
    }

    @Test
    void getAll() {
        ProductFilter productFilter = new ProductFilter(airForceProduct.getName(), null, null);
        Pageable pageable = PageRequest.of(0, 20);
        Page<Product> expected = new PageImpl<>(List.of(airForceProduct));
        doReturn(expected).when(productRepository).findAll(any(Predicate.class), any(Pageable.class));

        Page<Product> actual = productService.getAll(productFilter, pageable);

        assertEquals(expected, actual);
        verify(productRepository).findAll(any(Predicate.class), any(Pageable.class));
    }

    @Test
    void save() {
        Product expected = new Product();
        doReturn(expected).when(productRepository).save(expected);

        Product actual = productService.save(expected);

        assertEquals(expected, actual);
        verify(productRepository).save(expected);
    }

    @Test
    void update() {
        Product expected = airForceProduct;
        expected.setDescription("Updated");
        doReturn(expected).when(productRepository).saveAndFlush(expected);

        Product result = productService.update(expected);

        assertEquals(expected, result);
        verify(productRepository).saveAndFlush(expected);
    }

    @Test
    @DisplayName("Should throw ReferencedEntityException")
    void deleteById() {
        doReturn(Optional.of(sweaterProduct)).when(productRepository).findById(sweaterProduct.getId());
        doReturn(true).when(sellPositionRepository).existsByProduct(sweaterProduct);

        assertThrows(ReferencedEntityException.class,
                () -> productService.deleteById(sweaterProduct.getId()));

        verify(sellPositionRepository).existsByProduct(sweaterProduct);
        verify(productRepository, never()).deleteById(sweaterProduct.getId());
    }

    @Test
    void setProductImage() throws IOException {
        Product expected = sweaterProduct;
        expected.setImageId(imageId);
        MockMultipartFile imageFile = new MockMultipartFile("image", "image.jpg",
                "image/jpeg", new byte[]{1, 2, 3, 4});
        doReturn(Optional.of(sweaterProduct)).when(productRepository).findById(sweaterProduct.getId());
        doReturn(expected.getImageId()).when(productImageService).addProductImage(any(MockMultipartFile.class));
        doReturn(expected).when(productRepository).saveAndFlush(expected);

        Product actual = productService.setProductImage(sweaterProduct.getId(), imageFile);

        assertEquals(expected, actual);
        verify(productRepository).findById(sweaterProduct.getId());
        verify(productImageService).addProductImage(imageFile);
        verify(productRepository).saveAndFlush(sweaterProduct);
    }

    @Test
    void deleteProductImage() {
        doReturn(Optional.of(sweaterProduct)).when(productRepository).findById(sweaterProduct.getId());
        doNothing().when(productImageService).deleteProductImage(imageId);
        doReturn(sweaterProduct).when(productRepository).saveAndFlush(sweaterProduct);

        productService.deleteProductImage(sweaterProduct.getId());

        assertNull(sweaterProduct.getImageId());
        verify(productRepository).findById(sweaterProduct.getId());
        verify(productRepository).saveAndFlush(sweaterProduct);
    }
}
