package com.example.miracles_store.controller;

import com.example.miracles_store.config.TestConfig;
import com.example.miracles_store.dto.ProductTypeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
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
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getAll() throws Exception {
        List<ProductTypeDto> productTypes = List
                .of(new ProductTypeDto(1, "Shoes"), new ProductTypeDto(2, "T-shirts"));
        String expected = objectMapper.writeValueAsString(productTypes);

        String result = objectMapper.writeValueAsString(objectMapper
                .readTree(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productTypes"))
                        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString()).get("content"));

        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getById() throws Exception {
        int productTypeId = 2;
        String expected = objectMapper.writeValueAsString(new ProductTypeDto(2, "T-shirts"));

        String result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/productTypes/" + productTypeId))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void create() throws Exception {
        ProductTypeDto productTypeDto = new ProductTypeDto();
        productTypeDto.setName("Saved");
        String requestObject = objectMapper.writeValueAsString(productTypeDto);
        productTypeDto.setId(3);
        String expected = objectMapper.writeValueAsString(productTypeDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/productTypes")
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpectAll(status().isCreated()).andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(expected, result, false);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void update() throws Exception {
        ProductTypeDto productTypeDto = new ProductTypeDto(1, "Updated");
        String requestObject = objectMapper.writeValueAsString(productTypeDto);

        String result = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/productTypes")
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(requestObject, result, true);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void delete() throws Exception {
        int productTypeId = 2;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/productTypes/" + productTypeId))
                .andExpect(status().isOk());
    }
}
