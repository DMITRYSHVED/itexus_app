package com.example.miracles_store.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
public class ProductTypeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/sql/productTypes.sql")
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getAll() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/productTypes")
                .param("page", "0")
                .param("size", "20")
                .param("sort", "id,asc")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "page": 0,
                        "size": 20,
                        "sort": "id,asc"
                        }""");

        this.mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Shoes"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("T-shirts"));
    }

    @Test
    @Sql("/sql/productTypes.sql")
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getById() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/productTypes/2");

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 2,
                                    "name": "T-shirts"
                                }"""));
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void create() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.post("/api/v1/productTypes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "name": "Sweaters"
                        }""");

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "name": "Sweaters"
                                }"""));
    }

    @Test
    @Sql("/sql/productTypes.sql")
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void update() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.put("/api/v1/productTypes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "id": 1,
                        "name": "Updated"
                        }""");

        mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                    "id": 1,
                                    "name": "Updated"
                                }"""));
    }

    @Test
    @Sql("/sql/productTypes.sql")
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void delete() throws Exception {
        var requestBuilder = MockMvcRequestBuilders.get("/api/v1/productTypes/2");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }
}
