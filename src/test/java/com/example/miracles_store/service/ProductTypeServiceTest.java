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
        ProductType expected = shoesType;
        doReturn(Optional.of(shoesType)).when(productTypeRepository).findById(shoesType.getId());

        var actual = productTypeService.getById(shoesType.getId());

        assertEquals(expected, actual);
        verify(productTypeRepository).findById(shoesType.getId());
    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ProductType> expected = new PageImpl<>(List.of(shoesType));
        doReturn(expected).when(productTypeRepository).findAll(pageable);

        Page<ProductType> actual = productTypeService.getAll(pageable);

        assertEquals(expected, actual);
        verify(productTypeRepository).findAll(pageable);
    }

    @Test
    void save() {
        ProductType expected = new ProductType();
        doReturn(expected).when(productTypeRepository).save(expected);

        ProductType actual = productTypeService.save(expected);

        assertEquals(expected, actual);
        verify(productTypeRepository).save(expected);
    }

    @Test
    void update() {
        ProductType expected = shoesType;
        expected.setName("Updated");
        doReturn(expected).when(productTypeRepository).saveAndFlush(expected);

        ProductType actual = productTypeService.update(expected);

        assertEquals(expected, actual);
        verify(productTypeRepository).saveAndFlush(expected);
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
