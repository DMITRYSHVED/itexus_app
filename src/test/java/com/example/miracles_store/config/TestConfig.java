package com.example.miracles_store.config;

import com.example.miracles_store.repository.ProductRepository;
import com.example.miracles_store.repository.ProductTypeRepository;
import com.example.miracles_store.service.JwtService;
import com.example.miracles_store.service.ProductTypeService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @MockBean
    public JwtService jwtService;

    @Bean
    public ProductTypeService productTypeService(ProductTypeRepository productTypeRepository, ProductRepository productRepository) {
        return new ProductTypeService(productTypeRepository, productRepository);
    }
}
