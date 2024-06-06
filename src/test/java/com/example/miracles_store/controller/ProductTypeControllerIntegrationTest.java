package com.example.miracles_store.controller;

import com.example.miracles_store.config.TestConfig;
import com.example.miracles_store.constant.ProductTypeTestConstant;
import com.example.miracles_store.dto.ProductTypeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class ProductTypeControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    ProductTypeDto shoesTypeDto;

    ProductTypeDto sweatersTypeDto;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @BeforeEach
    void setUp() {
        shoesTypeDto = new ProductTypeDto(ProductTypeTestConstant.SHOES_TYPE_ID, ProductTypeTestConstant.TYPE_NAME_SHOES);

        sweatersTypeDto = new ProductTypeDto(ProductTypeTestConstant.SWEATERS_TYPE_ID, ProductTypeTestConstant.TYPE_NAME_SWEATERS);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_USER")
    void testUserRoleIsForbidden() throws Exception {
        mockMvc.perform(get(ProductTypeTestConstant.URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getAll() throws Exception {
        String expected = objectMapper.writeValueAsString(List.of(shoesTypeDto, sweatersTypeDto));

        String actual = objectMapper.writeValueAsString(objectMapper
                .readTree(mockMvc.perform(get(ProductTypeTestConstant.URL))
                        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString()).get("content"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getById() throws Exception {
        String expected = objectMapper.writeValueAsString(sweatersTypeDto);

        String actual = mockMvc.perform(get(ProductTypeTestConstant.URL + "/" +
                        ProductTypeTestConstant.SWEATERS_TYPE_ID)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void create() throws Exception {
        ProductTypeDto productTypeDto = new ProductTypeDto();
        productTypeDto.setName("Saved");
        String requestObject = objectMapper.writeValueAsString(productTypeDto);
        productTypeDto.setId(3);
        String expected = objectMapper.writeValueAsString(productTypeDto);

        String actual = mockMvc.perform(MockMvcRequestBuilders.post(ProductTypeTestConstant.URL)
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpectAll(status().isCreated()).andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void update() throws Exception {
        ProductTypeDto productTypeDto = new ProductTypeDto(shoesTypeDto.getId(), "Updated");
        String requestObject = objectMapper.writeValueAsString(productTypeDto);

        String actual = mockMvc.perform(MockMvcRequestBuilders.put(ProductTypeTestConstant.URL)
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(requestObject, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    @DisplayName("Throws ReferencedEntityException")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ProductTypeTestConstant.URL + "/" +
                ProductTypeTestConstant.SHOES_TYPE_ID)).andExpect(status().isBadRequest());
    }
}
