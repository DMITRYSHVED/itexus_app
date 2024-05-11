package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.AddressResponseDto;
import com.example.miracles_store.dto.OrderRequestDto;
import com.example.miracles_store.dto.OrderResponseDto;
import com.example.miracles_store.dto.SellPositionQuantityResponseDto;
import com.example.miracles_store.dto.UserResponseDto;
import com.example.miracles_store.entity.Address;
import com.example.miracles_store.entity.order.Order;
import com.example.miracles_store.entity.order.PositionOrder;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.service.AddressService;
import com.example.miracles_store.service.PositionOrderService;
import com.example.miracles_store.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public abstract class OrderMapper {

    @Autowired
    protected PositionOrderService positionOrderService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AddressService addressService;

    @Autowired
    protected AddressMapper addressMapper;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected SellPositionQuantityMapper sellPositionQuantityMapper;

    @Mapping(target = "address", source = "order.address")
    @Mapping(target = "user", source = "order.user")
    @Mapping(target = "orderSellPositions", expression = "java(setSellPositions(order))")
    public abstract OrderResponseDto entityToResponseDto(Order order);

    @Mapping(target = "address", source = "addressId")
    public abstract Order requestDtoToEntity(OrderRequestDto orderRequestDto);

    protected AddressResponseDto fromAddressToAddressResponseDto(Address address) {
        return addressMapper.toResponseDto(address);
    }

    protected UserResponseDto fromUserToUserResponseDto(User user) {
        return userMapper.entityToResponseDto(user);
    }

    protected List<SellPositionQuantityResponseDto> setSellPositions(Order order) {
        List<PositionOrder> positionOrders = positionOrderService.getOrderPositionOrderList(order);
        return positionOrders.stream().map(positionOrder -> sellPositionQuantityMapper
                .positionOrderToSellPositionQuantityDto(positionOrder)).toList();
    }

    protected Address fromAddressIdToAddress(Integer addressId) {
        return addressService.getById(addressId);
    }
}
