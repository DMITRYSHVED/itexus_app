package com.example.miracles_store.controller;

import com.example.miracles_store.dto.OrderCartRequestDto;
import com.example.miracles_store.dto.OrderCartResponseDto;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.mapper.OrderCartMapper;
import com.example.miracles_store.mapper.SellPositionQuantityMapper;
import com.example.miracles_store.service.OrderCartService;
import com.example.miracles_store.service.UserService;
import com.example.miracles_store.validator.group.CreateAction;
import com.example.miracles_store.validator.group.DeleteAction;
import com.example.miracles_store.validator.group.UpdateAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "orderCart")
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class OrderCartController {

    private final OrderCartService orderCartService;

    private final UserService userService;

    private final OrderCartMapper orderCartMapper;

    private final SellPositionQuantityMapper sellPositionQuantityMapper;

    @GetMapping
    public ResponseEntity<OrderCartResponseDto> getUserCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getByEmail(userDetails.getUsername());

        OrderCartResponseDto response = orderCartMapper.entityToResponseDto(orderCartService.getUserCart(user.getId()));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OrderCartResponseDto> addPositionToCart(@RequestBody @Validated
            ({Default.class, CreateAction.class}) OrderCartRequestDto orderCartRequestDto) {
        OrderCartResponseDto response = orderCartMapper.entityToResponseDto(orderCartService.
                saveSellPositionToCart(orderCartMapper.requestDtoToEntity(orderCartRequestDto)));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFromCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody
    @Validated({Default.class, DeleteAction.class}) OrderCartRequestDto sellPositionQuantityRequestDto) {
        User user = userService.getByEmail(userDetails.getUsername());

        orderCartService.deleteSellPositionFromCart(user.getId(), sellPositionQuantityMapper.
                orderCartRequestDtoToToEntity(sellPositionQuantityRequestDto));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping
    public ResponseEntity<OrderCartResponseDto> updatePositionQuantity(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Validated({Default.class, UpdateAction.class}) OrderCartRequestDto orderCartRequestDto) {
        User user = userService.getByEmail(userDetails.getUsername());

        OrderCartResponseDto response = orderCartMapper.entityToResponseDto(orderCartService.updatePositionQuantity(
                user.getId(), sellPositionQuantityMapper.orderCartRequestDtoToToEntity(orderCartRequestDto)));
        return ResponseEntity.ok(response);
    }
}
