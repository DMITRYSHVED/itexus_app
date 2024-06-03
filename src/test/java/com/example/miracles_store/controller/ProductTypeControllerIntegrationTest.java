package com.example.miracles_store.controller;

import com.example.miracles_store.config.TestConfig;
import com.example.miracles_store.constant.ProductTypeTestConstant;
import com.example.miracles_store.dto.ProductTypeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_USER")
    void testUserRoleIsForbidden() throws Exception {
        mockMvc.perform(get(ProductTypeTestConstant.URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getAll() throws Exception {
        String expected = objectMapper.writeValueAsString(List.of(new ProductTypeDto(ProductTypeTestConstant.TYPE_ID_1,
                ProductTypeTestConstant.TYPE_NAME_SHOES), new ProductTypeDto(ProductTypeTestConstant.TYPE_ID_2,
                ProductTypeTestConstant.TYPE_NAME_SWEATERS)));

        String result = objectMapper.writeValueAsString(objectMapper
                .readTree(mockMvc.perform(get(ProductTypeTestConstant.URL))
                        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString()).get("content"));

        Assertions.assertEquals(expected, result);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getById() throws Exception {
        String expected = objectMapper.writeValueAsString(new ProductTypeDto(ProductTypeTestConstant.TYPE_ID_2,
                ProductTypeTestConstant.TYPE_NAME_SWEATERS));

        String result = mockMvc.perform(get(ProductTypeTestConstant.URL_TYPE_ID_2))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void create() throws Exception {
        ProductTypeDto productTypeDto = new ProductTypeDto();
        productTypeDto.setName("Saved");
        String requestObject = objectMapper.writeValueAsString(productTypeDto);
        productTypeDto.setId(3);
        String expected = objectMapper.writeValueAsString(productTypeDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.post(ProductTypeTestConstant.URL)
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpectAll(status().isCreated()).andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, result);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void update() throws Exception {
        ProductTypeDto productTypeDto = new ProductTypeDto(ProductTypeTestConstant.TYPE_ID_1, "Updated");
        String requestObject = objectMapper.writeValueAsString(productTypeDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.put(ProductTypeTestConstant.URL)
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(requestObject, result);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ProductTypeTestConstant.URL_TYPE_ID_2))
                .andExpect(status().isOk());
    }
}
