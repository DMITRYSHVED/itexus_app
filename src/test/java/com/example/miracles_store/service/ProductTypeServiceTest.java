package com.example.miracles_store.service;

import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.repository.ProductTypeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ProductTypeServiceTest {

    @Autowired
    ProductTypeService productTypeService;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Test
    @Sql("/sql/productTypes.sql")
    void getById() {
        ProductType productType = productTypeService.getById(1);

        Assertions.assertThat(productType).isNotNull();
        Assertions.assertThat(productType.getName()).isEqualTo("Shoes");
    }

    @Test
    @Sql("/sql/productTypes.sql")
    void getAll() {
        Pageable pageable = PageRequest.of(0, 20);

        Page<ProductType> productTypes = productTypeService.getAll(pageable);

        Assertions.assertThat(productTypes).isNotNull();
        Assertions.assertThat(productTypes.getContent().get(0).getName()).isEqualTo("Shoes");
        Assertions.assertThat(productTypes.getContent().get(1).getName()).isEqualTo("T-shirts");
    }

    @Test
    void save() {
        ProductType productType = new ProductType();
        productType.setName("Denim");

        ProductType saved = productTypeService.save(productType);

        Assertions.assertThat(saved).isNotNull();
        Assertions.assertThat(saved.getName()).isEqualTo(productType.getName());
    }

    @Test
    @Sql("/sql/productTypes.sql")
    void update() {
        ProductType productType = new ProductType();
        productType.setId(1);
        productType.setName("Jeans");

        ProductType updated = productTypeService.update(productType);

        Assertions.assertThat(updated).isNotNull();
        Assertions.assertThat(updated.getName()).isEqualTo(productType.getName());
    }

    @Test
    @Sql("/sql/productTypes.sql")
    void deleteById() {
        productTypeService.deleteById(2);

        Optional<ProductType> productType = productTypeRepository.findById(2);

        Assertions.assertThat(productType).isEmpty();
    }
}
