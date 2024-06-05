package com.example.miracles_store.service;

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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductTypeService.class)
public class ProductTypeServiceTest {

    @MockBean
    ProductTypeRepository productTypeRepository;

    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductTypeService productTypeService;

    ProductType shoesType;

    @BeforeEach
    void setup() {
        shoesType = new ProductType(ProductTypeTestConstant.SHOES_TYPE_ID, ProductTypeTestConstant.TYPE_NAME_SHOES);
    }

    @Test
    void getById() {
        doReturn(Optional.of(shoesType)).when(productTypeRepository).findById(shoesType.getId());

        var result = productTypeService.getById(shoesType.getId());

        assertEquals(result, shoesType);
        verify(productTypeRepository).findById(shoesType.getId());
    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ProductType> expectedTypes = new PageImpl<>(List.of(shoesType));
        doReturn(expectedTypes).when(productTypeRepository).findAll(pageable);

        Page<ProductType> result = productTypeService.getAll(pageable);

        assertEquals(expectedTypes, result);
        verify(productTypeRepository).findAll(pageable);
    }

    @Test
    void save() {
        ProductType savedType = new ProductType();
        doReturn(savedType).when(productTypeRepository).save(savedType);

        ProductType result = productTypeService.save(savedType);

        assertEquals(result, savedType);
        verify(productTypeRepository).save(savedType);
    }

    @Test
    void update() {
        ProductType updatedType = shoesType;
        updatedType.setName("Updated");
        doReturn(updatedType).when(productTypeRepository).saveAndFlush(updatedType);

        ProductType result = productTypeService.update(updatedType);

        assertEquals(result, updatedType);
        verify(productTypeRepository).saveAndFlush(updatedType);
    }

    @Test
    @DisplayName("Should throw ReferencedEntityException")
    void deleteById() {
        doReturn(Optional.of(shoesType)).when(productTypeRepository).findById(shoesType.getId());
        doReturn(true).when(productRepository).existsByProductType(shoesType);

        assertThrows(ReferencedEntityException.class,
                () -> productTypeService.deleteById(shoesType.getId()));

        verify(productRepository).existsByProductType(shoesType);
        verify(productTypeRepository, never()).deleteById(shoesType.getId());
    }
}
