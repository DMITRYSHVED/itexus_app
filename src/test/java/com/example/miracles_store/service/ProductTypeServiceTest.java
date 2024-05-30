package com.example.miracles_store.service;

import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.exception.ReferencedEntityException;
import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.repository.ProductTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class ProductTypeServiceTest {

    @Mock
    ProductTypeRepository productTypeRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductTypeService productTypeService;

    @Test
    void getById() {
        int productTypeId = 1;
        ProductType productType = new ProductType(1, "Shoes");
        doReturn(Optional.of(productType)).when(productTypeRepository).findById(1);

        var result = productTypeService.getById(productTypeId);

        assertNotNull(result);
        assertEquals(result.getName(), "Shoes");
        verify(productTypeRepository).findById(1);
        verifyNoMoreInteractions(productTypeRepository);
    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ProductType> types = new PageImpl<>(List
                .of(new ProductType(1, "Shoes"), new ProductType(2, "T-shirts")));
        doReturn(types).when(productTypeRepository).findAll(pageable);

        Page<ProductType> result = productTypeService.getAll(pageable);

        assertNotNull(result);
        Assertions.assertThat(result.getContent().get(0).getName()).isEqualTo(types.getContent().get(0).getName());
        Assertions.assertThat(result.getContent().get(1).getName()).isEqualTo(types.getContent().get(1).getName());
        verify(productTypeRepository).findAll(pageable);
        verifyNoMoreInteractions(productTypeRepository);
    }


    @Test
    void save() {
        ProductType expected = new ProductType(3, "Denim");
        ProductType saved = new ProductType();
        saved.setName("Denim");
        doReturn(expected).when(productTypeRepository).save(saved);

        ProductType result = productTypeService.save(saved);

        assertNotNull(result);
        assertEquals(result, expected);
        verify(productTypeRepository).save(saved);
        verifyNoMoreInteractions(productTypeRepository);
    }

    @Test
    void update() {
        ProductType updated = new ProductType(3, "Denim");
        doReturn(updated).when(productTypeRepository).saveAndFlush(updated);

        ProductType result = productTypeService.update(updated);

        assertNotNull(result);
        assertEquals(result, updated);
        verify(productTypeRepository).saveAndFlush(updated);
        verifyNoMoreInteractions(productTypeRepository);
    }

    @Test
    @DisplayName("Should throw ReferencedEntityException")
    void deleteById() {
        int productTypeId = 1;
        ProductType productType = new ProductType(productTypeId, "Shoes");
        ProductTypeService spyProductTypeService = spy(productTypeService);
        doReturn(productType).when(spyProductTypeService).getById(productTypeId);
        doReturn(true).when(productRepository).existsByProductType(productType);

        ReferencedEntityException exception = assertThrows(ReferencedEntityException.class, () ->
                spyProductTypeService.deleteById(productTypeId)
        );

        assertEquals("Can't delete type 'Shoes' due to existing products of this type", exception.getMessage());
        verify(productRepository).existsByProductType(productType);
        verify(productTypeRepository, never()).deleteById(productTypeId);
    }
}
