package com.example.miracles_store.repository;

import com.example.miracles_store.constant.ProductTestConstant;
import com.example.miracles_store.constant.SellPositionTestConstant;
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
public class SellPositionRepositoryIntegrationTest {

    @Autowired
    SellPositionRepository sellPositionRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @Test
    void existsByProductAndSize() {
        assertTrue(sellPositionRepository.existsByProductAndSize(ProductTestConstant.SWEATER_PRODUCT_ID,
                SellPositionTestConstant.POSITION_SIZE_SWEATER));
    }

    @Test
    void existsByProductAndSizeAndIdNotEquals() {
        assertTrue(sellPositionRepository.existsByProductAndSizeAndIdNotEquals(ProductTestConstant.AIR_FORCE_PRODUCT_ID,
                SellPositionTestConstant.POSITION_SIZE_AIR_FORCE, SellPositionTestConstant.SWEATER_POSITION_ID));
    }

    @Test
    void existsByProduct() {
        assertTrue(sellPositionRepository.existsByProduct(SellPositionTestConstant.POSITION_PRODUCT_SWEATER));
    }
}
