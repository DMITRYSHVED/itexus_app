package com.example.miracles_store.repository;

import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.exception.ObjectNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductTypeRepositoryIntegrationTest {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @Test
    void existsByName() {
        assertTrue(productTypeRepository.existsByName("Shoes"));
    }

    @Test
    void existsByNameAndIdNotEqual() {
        assertTrue(productTypeRepository.existsByNameAndIdNotEqual("Shoes", 123));
    }

    @Test
    void findById() {
        ProductType productType = productTypeRepository.findById(2)
                .orElseThrow(() -> new ObjectNotFoundException("Can't find productType"));

        assertNotNull(productType);
        assertEquals(productType.getName(), "T-shirts");
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 20);

        Page<ProductType> productTypes = productTypeRepository.findAll(pageable);

        assertNotNull(productTypes);
        assertEquals(productTypes.getContent().get(0).getName(), "Shoes");
        assertEquals(productTypes.getContent().get(1).getName(), "T-shirts");
    }

    @Test
    void save() {
        ProductType productType = new ProductType();
        productType.setName("Denim");

        ProductType saved = productTypeRepository.save(productType);

        assertNotNull(saved);
        assertEquals(saved.getName(), productType.getName());
    }

    @Test
    void update() {
        ProductType productType = new ProductType();
        productType.setId(1);
        productType.setName("Jeans");

        ProductType updated = productTypeRepository.saveAndFlush(productType);

        assertNotNull(updated);
        assertEquals(updated.getName(), productType.getName());
    }

    @Test
    void deleteById() {
        productTypeRepository.deleteById(2);

        Optional<ProductType> productType = productTypeRepository.findById(2);

        Assertions.assertThat(productType).isEmpty();
    }
}
