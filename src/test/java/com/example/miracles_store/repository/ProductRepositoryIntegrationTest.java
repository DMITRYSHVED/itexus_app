package com.example.miracles_store.repository;

import com.example.miracles_store.constant.ProductTestConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryIntegrationTest {

    @Autowired
    ProductRepository productRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @Test
    void existsByName() {
        assertTrue(productRepository.existsByName(ProductTestConstant.PRODUCT_NAME_AIR_FORCE));
    }

    @Test
    void existsByNameAndIdNotEqual() {
        assertTrue(productRepository.existsByNameAndIdNotEqual(ProductTestConstant.PRODUCT_NAME_SWEATER,
                ProductTestConstant.AIR_FORCE_PRODUCT_ID));
    }

    @Test
    void existsByProductType() {
        assertTrue(productRepository.existsByProductType(ProductTestConstant.PRODUCT_TYPE_SWEATER));
    }
}
