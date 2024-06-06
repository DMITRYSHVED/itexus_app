package com.example.miracles_store.config;

import com.example.miracles_store.service.JwtService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestConfig {

    @MockBean
    public JwtService jwtService;
}
