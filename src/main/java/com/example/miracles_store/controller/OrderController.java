package com.example.miracles_store.controller;

import com.example.miracles_store.dto.OrderRequestDto;
import com.example.miracles_store.dto.OrderResponseDto;
import com.example.miracles_store.dto.filter.OrderFilter;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.entity.order.Order;
import com.example.miracles_store.mapper.OrderMapper;
import com.example.miracles_store.service.OrderService;
import com.example.miracles_store.service.UserService;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.UpdateAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "order")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getAll(OrderFilter orderFilter,
                                                         @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        Page<OrderResponseDto> response = orderService.getAll(orderFilter, pageable).
                map(orderMapper::entityToResponseDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getById(@PathVariable("id") Integer id) {
        OrderResponseDto response = orderMapper.entityToResponseDto(orderService.getById(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody @Validated({Default.class, CreateAction.class}) OrderRequestDto orderRequestDto) {
        User user = userService.getByEmail(userDetails.getUsername());
        Order order = orderMapper.requestDtoToEntity(orderRequestDto);

        order.setUser(user);
        OrderResponseDto response = orderMapper.entityToResponseDto(orderService.saveOrder(order,
                orderRequestDto.getSellPositionQuantitySet()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<OrderResponseDto> update(@RequestBody @Validated({Default.class, UpdateAction.class})
                                                   OrderRequestDto orderRequestDto) {
        OrderResponseDto response = orderMapper.entityToResponseDto(orderService.update(orderMapper.
                requestDtoToEntity(orderRequestDto)));
        return ResponseEntity.ok(response);
    }
}
