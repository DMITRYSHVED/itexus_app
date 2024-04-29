package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.OrderCartRequestDto;
import com.example.miracles_store.dto.OrderCartResponseDto;
import com.example.miracles_store.dto.SellPositionQuantityDto;
import com.example.miracles_store.dto.UserResponseDto;
import com.example.miracles_store.entity.OrderCart;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.entity.SellPositionQuantity;
import com.example.miracles_store.entity.User;
import com.example.miracles_store.exception.OutOfStockException;
import com.example.miracles_store.service.SellPositionService;
import com.example.miracles_store.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Mapper
public abstract class OrderCartMapper {

    @Autowired
    protected UserService userService;

    @Autowired protected SellPositionService sellPositionService;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected SellPositionMapper sellPositionMapper;

    @Autowired
    protected SellPositionQuantityMapper sellPositionQuantityMapper;

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "sellPositionQuantityList", source = "orderCartRequestDto")
    public abstract OrderCart requestDtoToEntity(OrderCartRequestDto orderCartRequestDto);

    @Mapping(target = "user", source = "id", qualifiedByName = "fromIdToUserResponseDto")
    @Mapping(target = "sellPositionQuantityDtoList", source = "sellPositionQuantityList",
            qualifiedByName = "fromSellPositionQuantityToSellPositionQuantityDto")
    public abstract OrderCartResponseDto entityToResponseDto(OrderCart orderCart);


    @Named("fromIdToUserResponseDto")
    protected UserResponseDto fromIdToUserResponseDto(Integer id) {
        User user = userService.getById(id);
        return userMapper.entityToResponseDto(user);
    }

    @Named("fromSellPositionQuantityToSellPositionQuantityDto")
    protected List<SellPositionQuantityDto> fromSellPositionQuantityToSellPositionQuantityDto(
            List<SellPositionQuantity> sellPositionQuantityList) {
        return sellPositionQuantityList.stream().map(sellPositionQuantityMapper::entityToDto).toList();
    }

    protected List<SellPositionQuantity> fromDtoToSellPositionQuantityList(OrderCartRequestDto orderCartRequestDto) {
        List<SellPositionQuantity> sellPositionQuantityList = new ArrayList<>();

        if (orderCartRequestDto.getQuantity() == null) {
            SellPosition sellPosition = sellPositionService.getById(orderCartRequestDto.getSellPositionId());
            if (sellPosition.getQuantity() < 1) {
                throw new OutOfStockException("Out-of-stock item: " + sellPosition.getProduct().getName());
            }
            orderCartRequestDto.setQuantity(1);
        }
        sellPositionQuantityList.add(new SellPositionQuantity(orderCartRequestDto.getSellPositionId(),
                orderCartRequestDto.getQuantity()));
        return sellPositionQuantityList;
    }
}
