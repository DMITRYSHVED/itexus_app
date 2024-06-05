package com.example.miracles_store.controller;


import com.example.miracles_store.config.TestConfig;
import com.example.miracles_store.constant.ProductTestConstant;
import com.example.miracles_store.constant.SellPositionTestConstant;
import com.example.miracles_store.dto.ProductResponseDto;
import com.example.miracles_store.dto.ProductTypeDto;
import com.example.miracles_store.dto.SellPositionRequestDto;
import com.example.miracles_store.dto.SellPositionResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@Testcontainers
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class SellPositionControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    SellPositionResponseDto airForcePositionResponseDto;

    SellPositionResponseDto sweaterPositionResponseDto;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @BeforeEach
    void setUp() {
        airForcePositionResponseDto = new SellPositionResponseDto(SellPositionTestConstant.AIR_FORCE_POSITION_ID,
                new ProductResponseDto(ProductTestConstant.AIR_FORCE_PRODUCT_ID,
                        ProductTestConstant.PRODUCT_NAME_AIR_FORCE, ProductTestConstant.PRODUCT_DESCRIPTION_AIR_FORCE,
                        ProductTestConstant.PRODUCT_COST_AIR_FORCE,
                        new ProductTypeDto(ProductTestConstant.PRODUCT_TYPE_AIR_FORCE.getId(),
                                ProductTestConstant.PRODUCT_TYPE_AIR_FORCE.getName()), null),
                SellPositionTestConstant.POSITION_SIZE_AIR_FORCE, SellPositionTestConstant.POSITION_QUANTITY_AIR_FORCE,
                SellPositionTestConstant.POSITION_ACTIVE_AIR_FORCE);

        sweaterPositionResponseDto = new SellPositionResponseDto(SellPositionTestConstant.SWEATER_POSITION_ID,
                new ProductResponseDto(ProductTestConstant.SWEATER_PRODUCT_ID,
                        ProductTestConstant.PRODUCT_NAME_SWEATER, ProductTestConstant.PRODUCT_DESCRIPTION_SWEATER,
                        ProductTestConstant.PRODUCT_COST_SWEATER,
                        new ProductTypeDto(ProductTestConstant.PRODUCT_TYPE_SWEATER.getId(),
                                ProductTestConstant.PRODUCT_TYPE_SWEATER.getName()), null),
                SellPositionTestConstant.POSITION_SIZE_SWEATER, SellPositionTestConstant.POSITION_QUANTITY_SWEATER,
                SellPositionTestConstant.POSITION_ACTIVE_SWEATER);
    }

    @Test
    void getAll() throws Exception {
        String expected = objectMapper.writeValueAsString(List.of(airForcePositionResponseDto, sweaterPositionResponseDto));

        String result = objectMapper.writeValueAsString(objectMapper.readTree(mockMvc.perform(get(SellPositionTestConstant.URL))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString()).get("content"));

        Assertions.assertEquals(expected, result);
    }

    @Test
    void getById() throws Exception {
        String expected = objectMapper.writeValueAsString(sweaterPositionResponseDto);

        String result = mockMvc.perform(get(SellPositionTestConstant.URL + "/" +
                        SellPositionTestConstant.SWEATER_POSITION_ID)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void create() throws Exception {
        SellPositionRequestDto sellPositionRequestDto = new SellPositionRequestDto(null, ProductTestConstant.AIR_FORCE_PRODUCT_ID, "L",
                10, true);
        SellPositionResponseDto sellPositionResponseDto = new SellPositionResponseDto(3, airForcePositionResponseDto.getProduct(),
                sellPositionRequestDto.getSize(), sellPositionRequestDto.getQuantity(), sellPositionRequestDto.getIsActive());
        String requestObject = objectMapper.writeValueAsString(sellPositionRequestDto);
        String expected = objectMapper.writeValueAsString(sellPositionResponseDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.post(SellPositionTestConstant.URL)
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpectAll(status().isCreated()).andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void update() throws Exception {
        SellPositionRequestDto sellPositionRequestDto = new SellPositionRequestDto(sweaterPositionResponseDto.getId(),
                sweaterPositionResponseDto.getProduct().getId(), "Updated", sweaterPositionResponseDto.getQuantity(),
                sweaterPositionResponseDto.getIsActive());
        SellPositionResponseDto sellPositionResponseDto = sweaterPositionResponseDto;
        sweaterPositionResponseDto.setSize(sellPositionRequestDto.getSize());
        String requestObject = objectMapper.writeValueAsString(sellPositionRequestDto);
        String expected = objectMapper.writeValueAsString(sellPositionResponseDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.put(SellPositionTestConstant.URL)
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, result);
    }
}
