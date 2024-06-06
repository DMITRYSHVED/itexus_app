package com.example.miracles_store.controller;

import com.example.miracles_store.config.TestConfig;
import com.example.miracles_store.constant.ProductTestConstant;
import com.example.miracles_store.constant.ProductTypeTestConstant;
import com.example.miracles_store.dto.ProductRequestDto;
import com.example.miracles_store.dto.ProductResponseDto;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Testcontainers
@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class ProductControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    ProductResponseDto airForceResponseDto;

    ProductResponseDto sweaterResponseDto;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @BeforeEach
    void setUp() {
        airForceResponseDto = new ProductResponseDto(ProductTestConstant.AIR_FORCE_PRODUCT_ID,
                ProductTestConstant.PRODUCT_NAME_AIR_FORCE, ProductTestConstant.PRODUCT_DESCRIPTION_AIR_FORCE,
                ProductTestConstant.PRODUCT_COST_AIR_FORCE,
                new ProductTypeDto(ProductTestConstant.PRODUCT_TYPE_AIR_FORCE.getId(),
                        ProductTestConstant.PRODUCT_TYPE_AIR_FORCE.getName()), null);

        sweaterResponseDto = new ProductResponseDto(ProductTestConstant.SWEATER_PRODUCT_ID,
                ProductTestConstant.PRODUCT_NAME_SWEATER, ProductTestConstant.PRODUCT_DESCRIPTION_SWEATER,
                ProductTestConstant.PRODUCT_COST_SWEATER,
                new ProductTypeDto(ProductTestConstant.PRODUCT_TYPE_SWEATER.getId(),
                        ProductTestConstant.PRODUCT_TYPE_SWEATER.getName()), null);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_USER")
    void testUserRoleIsForbidden() throws Exception {
        mockMvc.perform(get(ProductTestConstant.URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getAll() throws Exception {
        String expected = objectMapper.writeValueAsString(List.of(airForceResponseDto, sweaterResponseDto));

        String actual = objectMapper.writeValueAsString(objectMapper.readTree(mockMvc.perform(get(ProductTestConstant.URL))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString()).get("content"));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void getById() throws Exception {
        String expected = objectMapper.writeValueAsString(sweaterResponseDto);

        String actual = mockMvc.perform(get(ProductTestConstant.URL + "/" +
                        ProductTestConstant.SWEATER_PRODUCT_ID)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void create() throws Exception {
        ProductRequestDto productRequestDto = new ProductRequestDto(null, "Saved", "Desc",
                BigDecimal.ONE, ProductTypeTestConstant.SHOES_TYPE_ID);
        ProductResponseDto productResponseDto = new ProductResponseDto(3, productRequestDto.getName(),
                productRequestDto.getDescription(), productRequestDto.getCost(),
                new ProductTypeDto(ProductTypeTestConstant.SHOES_TYPE_ID, ProductTypeTestConstant.TYPE_NAME_SHOES), null);
        String requestObject = objectMapper.writeValueAsString(productRequestDto);
        String expected = objectMapper.writeValueAsString(productResponseDto);

        String actual = mockMvc.perform(MockMvcRequestBuilders.post(ProductTestConstant.URL)
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpectAll(status().isCreated()).andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void update() throws Exception {
        ProductRequestDto productRequestDto = new ProductRequestDto(sweaterResponseDto.getId(),
                sweaterResponseDto.getName(), "Updated", sweaterResponseDto.getCost(),
                sweaterResponseDto.getProductType().getId());
        ProductResponseDto productResponseDto = sweaterResponseDto;
        sweaterResponseDto.setDescription(productRequestDto.getDescription());
        String requestObject = objectMapper.writeValueAsString(productRequestDto);
        String expected = objectMapper.writeValueAsString(productResponseDto);

        String actual = mockMvc.perform(MockMvcRequestBuilders.put(ProductTestConstant.URL)
                        .contentType(MediaType.APPLICATION_JSON).content(requestObject))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void setProductImage() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg",
                MediaType.IMAGE_JPEG_VALUE, "image content".getBytes());
        ProductResponseDto expectedResponse = airForceResponseDto;
        airForceResponseDto.setEncodedImage(Base64.getEncoder().encodeToString(image.getInputStream().readAllBytes()));
        String expected = objectMapper.writeValueAsString(expectedResponse);

        String actual = mockMvc.perform(MockMvcRequestBuilders.multipart(ProductTestConstant.URL + "/" +
                                ProductTestConstant.AIR_FORCE_PRODUCT_ID).file(image)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    void deleteProductImage() throws Exception {
        ProductResponseDto expectedResponse = sweaterResponseDto;
        String expected = objectMapper.writeValueAsString(expectedResponse);

        String actual = mockMvc.perform(MockMvcRequestBuilders.delete(ProductTestConstant.URL + "/deleteImage/" +
                        ProductTestConstant.SWEATER_PRODUCT_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @WithMockUser(username = "Tester", authorities = "ROLE_ADMIN")
    @DisplayName("Throws ReferencedEntityException")
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ProductTestConstant.URL + "/" +
                ProductTestConstant.AIR_FORCE_PRODUCT_ID)).andExpect(status().isBadRequest());
    }
}
