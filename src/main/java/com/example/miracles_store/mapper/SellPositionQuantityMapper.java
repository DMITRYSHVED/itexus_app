package com.example.miracles_store.mapper;

import com.example.miracles_store.entity.order.SellPositionQuantityResponse;
import com.example.miracles_store.dto.SellPositionResponseDto;
import com.example.miracles_store.entity.PositionOrder;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.entity.order.SellPositionQuantity;
import com.example.miracles_store.service.SellPositionService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class SellPositionQuantityMapper {

    @Autowired
    protected SellPositionService sellPositionService;

    @Autowired
    protected SellPositionMapper sellPositionMapper;

    @Mapping(target = "sellPositionId", source = "sellPositionResponseDto")
    public abstract SellPositionQuantity dtoToEntity(SellPositionQuantityResponse sellPositionQuantityResponse);

    @Mapping(target = "sellPositionResponseDto", source = "sellPositionId")
    public abstract SellPositionQuantityResponse entityToResponseDto(SellPositionQuantity sellPositionQuantity);

    @Mapping(target = "sellPositionResponseDto", source = "sellPosition",
            qualifiedByName = "fromSellPositionToSellPositionResponseDto")
    public abstract SellPositionQuantityResponse positionOrderToSellPositionQuantityDto(PositionOrder positionOrder);

    @Named("fromSellPositionToSellPositionResponseDto")
    protected SellPositionResponseDto fromSellPositionToSellPositionResponseDto(SellPosition sellPosition) {
        return sellPositionMapper.entityToResponseDto(sellPosition);
    }

    protected SellPositionResponseDto sellPositionIdToSellPositionResponseDto(Integer sellPositionId) {
        SellPosition sellPosition = sellPositionService.getById(sellPositionId);
        return sellPositionMapper.entityToResponseDto(sellPosition);
    }

    protected Integer sellPositionResponseDtoToSellPositionId(SellPositionResponseDto sellPositionResponseDto) {
        return sellPositionResponseDto.getId();
    }
}
