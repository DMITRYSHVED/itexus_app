package com.example.miracles_store.mapper;

import com.example.miracles_store.dto.SellPositionQuantityDto;
import com.example.miracles_store.dto.SellPositionResponseDto;
import com.example.miracles_store.entity.PositionOrder;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.entity.SellPositionQuantity;
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
    public abstract SellPositionQuantity dtoToEntity(SellPositionQuantityDto sellPositionQuantity);

    @Mapping(target = "sellPositionResponseDto", source = "sellPositionId")
    public abstract SellPositionQuantityDto entityToDto(SellPositionQuantity sellPositionQuantity);

    @Mapping(target = "sellPositionResponseDto", source = "sellPosition",
            qualifiedByName = "fromSellPositionToSellPositionResponseDto")
    public abstract SellPositionQuantityDto positionOrderToSellPositionQuantityDto(PositionOrder positionOrder);

    @Named("fromSellPositionToSellPositionResponseDto")
    protected SellPositionResponseDto fromSellPositionToSellPositionResponseDto(SellPosition sellPosition) {
        return sellPositionMapper.EntityToResponseDto(sellPosition);
    }

    protected SellPositionResponseDto sellPositionIdToSellPositionResponseDto(Integer sellPositionId) {
        SellPosition sellPosition = sellPositionService.getById(sellPositionId);
        return sellPositionMapper.EntityToResponseDto(sellPosition);
    }

    protected Integer sellPositionResponseDtoToSellPositionId(SellPositionResponseDto sellPositionResponseDto) {
        return sellPositionResponseDto.getId();
    }
}
