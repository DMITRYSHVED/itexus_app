package com.example.miracles_store.controller;

import com.example.miracles_store.dto.OrderRequestDto;
import com.example.miracles_store.dto.OrderResponseDto;
import com.example.miracles_store.dto.filter.OrderFilter;
import com.example.miracles_store.mapper.OrderMapper;
import com.example.miracles_store.service.OrderService;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "order")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAll(OrderFilter orderFilter,
                                                         @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        List<OrderResponseDto> response = orderService.getAll(orderFilter, pageable).getContent().stream()
                .map(orderMapper::entityToResponseDto).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getById(@PathVariable("id") Integer id) {
        OrderResponseDto response = orderMapper.entityToResponseDto(orderService.getById(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(
            @RequestBody @Validated({Default.class, CreateAction.class}) OrderRequestDto orderRequestDto) {
        OrderResponseDto response = orderMapper.entityToResponseDto(orderService
                .saveOrder(orderMapper.requestDtoToEntity(orderRequestDto), orderRequestDto.getSellPositionQuantitySet()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<OrderResponseDto> update(@RequestBody @Validated({Default.class, UpdateAction.class})
                                                   OrderRequestDto orderRequestDto) {
        OrderResponseDto response = orderMapper.
                entityToResponseDto(orderService.update(orderMapper.requestDtoToEntity(orderRequestDto)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
