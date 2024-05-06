package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.OrderCartRequestDto;
import com.example.miracles_store.dto.OrderCartResponseDto;
import com.example.miracles_store.entity.order.SellPositionQuantityResponse;
import com.example.miracles_store.dto.UserResponseDto;
import com.example.miracles_store.entity.OrderCart;
import com.example.miracles_store.entity.order.SellPositionQuantity;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.service.SellPositionService;
import com.example.miracles_store.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper
public abstract class OrderCartMapper {

    @Autowired
    protected UserService userService;

    @Autowired
    protected SellPositionService sellPositionService;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected SellPositionMapper sellPositionMapper;

    @Autowired
    protected SellPositionQuantityMapper sellPositionQuantityMapper;

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "sellPositionQuantitySet", source = "orderCartRequestDto")
    public abstract OrderCart requestDtoToEntity(OrderCartRequestDto orderCartRequestDto);

    @Mapping(target = "user", source = "id", qualifiedByName = "fromIdToUserResponseDto")
    @Mapping(target = "sellPositionQuantityDtoSet", source = "sellPositionQuantitySet",
            qualifiedByName = "fromSellPositionQuantityToSellPositionQuantityResponse")
    public abstract OrderCartResponseDto entityToResponseDto(OrderCart orderCart);


    @Named("fromIdToUserResponseDto")
    protected UserResponseDto fromIdToUserResponseDto(Integer id) {
        User user = userService.getById(id);
        return userMapper.entityToResponseDto(user);
    }

    @Named("fromSellPositionQuantityToSellPositionQuantityResponse")
    protected Set<SellPositionQuantityResponse> fromSellPositionQuantityToSellPositionQuantityDto(
            Set<SellPositionQuantity> sellPositionQuantitySet) {
        return sellPositionQuantitySet.stream().map(sellPositionQuantityMapper::entityToResponseDto)
                .collect(Collectors.toSet());
    }

    protected Set<SellPositionQuantity> fromDtoToSellPositionQuantitySet(OrderCartRequestDto orderCartRequestDto) {
        Set<SellPositionQuantity> sellPositionQuantitySet = new HashSet<>();

        sellPositionQuantitySet.add(new SellPositionQuantity(orderCartRequestDto.getSellPositionId(),
                orderCartRequestDto.getQuantity()));
        return sellPositionQuantitySet;
    }
}
