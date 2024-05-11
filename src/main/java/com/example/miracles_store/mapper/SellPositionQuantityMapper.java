package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.OrderCartRequestDto;
import com.example.miracles_store.dto.SellPositionQuantityResponseDto;
import com.example.miracles_store.dto.SellPositionResponseDto;
import com.example.miracles_store.entity.order.PositionOrder;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.entity.order.SellPositionQuantity;
import com.example.miracles_store.service.SellPositionService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class SellPositionQuantityMapper {

    @Autowired
    protected SellPositionService sellPositionService;

    @Autowired
    protected SellPositionMapper sellPositionMapper;

    public abstract SellPositionQuantity orderCartRequestDtoToToEntity(OrderCartRequestDto orderCartRequestDto);

    @Mapping(target = "sellPositionResponseDto", source = "sellPositionId")
    public abstract SellPositionQuantityResponseDto entityToResponseDto(SellPositionQuantity sellPositionQuantity);

    @Mapping(target = "sellPositionResponseDto", source = "sellPosition")
    public abstract SellPositionQuantityResponseDto positionOrderToSellPositionQuantityDto(PositionOrder positionOrder);

    protected SellPositionResponseDto fromSellPositionToSellPositionResponseDto(SellPosition sellPosition) {
        return sellPositionMapper.entityToResponseDto(sellPosition);
    }

    protected SellPositionResponseDto sellPositionIdToSellPositionResponseDto(Integer sellPositionId) {
        SellPosition sellPosition = sellPositionService.getById(sellPositionId);
        return sellPositionMapper.entityToResponseDto(sellPosition);
    }
}
