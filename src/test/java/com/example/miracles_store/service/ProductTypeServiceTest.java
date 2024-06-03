package com.example.miracles_store.service;

import com.example.miracles_store.config.TestConfig;
import com.example.miracles_store.constant.ProductTypeTestConstant;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.exception.ReferencedEntityException;
import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.repository.ProductTypeRepository;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProductTypeServiceTest {

    @MockBean
    private ProductTypeRepository productTypeRepository;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeService productTypeService;

    private ProductType productType;

    @BeforeEach
    public void setup() {
        productType = new ProductType(ProductTypeTestConstant.TYPE_ID_1, ProductTypeTestConstant.TYPE_NAME_SHOES);
    }

    @Test
    void getById() {
        doReturn(Optional.of(productType)).when(productTypeRepository).findById(ProductTypeTestConstant.TYPE_ID_1);

        var result = productTypeService.getById(ProductTypeTestConstant.TYPE_ID_1);

        assertNotNull(result);
        assertEquals(result, productType);
        verify(productTypeRepository).findById(ProductTypeTestConstant.TYPE_ID_1);
    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ProductType> types = new PageImpl<>(List
                .of(new ProductType(ProductTypeTestConstant.TYPE_ID_1, ProductTypeTestConstant.TYPE_NAME_SHOES),
                        new ProductType(ProductTypeTestConstant.TYPE_ID_2, ProductTypeTestConstant.TYPE_NAME_SWEATERS)));
        doReturn(types).when(productTypeRepository).findAll(pageable);

        Page<ProductType> result = productTypeService.getAll(pageable);

        assertNotNull(result);
        assertEquals(types, result);
        verify(productTypeRepository).findAll(pageable);
    }

    @Test
    void save() {
        ProductType expected = new ProductType(3, "Saved");
        ProductType saved = new ProductType();
        saved.setName("Saved");
        doReturn(expected).when(productTypeRepository).save(saved);

        ProductType result = productTypeService.save(saved);

        assertNotNull(result);
        assertEquals(result, expected);
        verify(productTypeRepository).save(saved);
    }

    @Test
    void update() {
        ProductType updated = new ProductType(3, "Updated");
        doReturn(updated).when(productTypeRepository).saveAndFlush(updated);

        ProductType result = productTypeService.update(updated);

        assertNotNull(result);
        assertEquals(result, updated);
        verify(productTypeRepository).saveAndFlush(updated);
    }

    @Test
    @DisplayName("Should throw ReferencedEntityException")
    void deleteById() {
        doReturn(Optional.of(productType)).when(productTypeRepository).findById(ProductTypeTestConstant.TYPE_ID_1);
        doReturn(true).when(productRepository).existsByProductType(productType);

        assertThrows(ReferencedEntityException.class,
                () -> productTypeService.deleteById(ProductTypeTestConstant.TYPE_ID_1));

        verify(productRepository).existsByProductType(productType);
        verify(productTypeRepository, never()).deleteById(ProductTypeTestConstant.TYPE_ID_1);
    }
}
