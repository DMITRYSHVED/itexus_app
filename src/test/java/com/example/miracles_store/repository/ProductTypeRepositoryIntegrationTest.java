package com.example.miracles_store.repository;

import com.example.miracles_store.constant.ProductTypeTestConstant;
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
public class ProductTypeRepositoryIntegrationTest {

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @Test
    void existsByName() {
        assertTrue(productTypeRepository.existsByName(ProductTypeTestConstant.TYPE_NAME_SHOES));
    }

    @Test
    void existsByNameAndIdNotEqual() {
        assertTrue(productTypeRepository.existsByNameAndIdNotEqual(ProductTypeTestConstant.TYPE_NAME_SWEATERS, 123));
    }
}
