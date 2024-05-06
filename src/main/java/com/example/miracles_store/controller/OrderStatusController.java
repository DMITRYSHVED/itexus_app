package com.example.miracles_store.controller;

import com.example.miracles_store.entity.enums.OrderStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Tag(name = "orderStatus")
@RestController
@RequestMapping("/api/v1/orderStatuses")
@RequiredArgsConstructor
public class OrderStatusController {

    @GetMapping
    public ResponseEntity<List<OrderStatus>> getAll() {
        return new ResponseEntity<>(Arrays.asList(OrderStatus.values()), HttpStatus.OK);
    }
}
