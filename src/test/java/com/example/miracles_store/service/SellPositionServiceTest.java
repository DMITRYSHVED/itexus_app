package com.example.miracles_store.service;

import com.example.miracles_store.constant.SellPositionTestConstant;
import com.example.miracles_store.dto.filter.SellPositionFilter;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.entity.order.Order;
import com.example.miracles_store.entity.order.PositionOrder;
import com.example.miracles_store.repository.SellPositionRepository;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SellPositionService.class)
public class SellPositionServiceTest {

    @MockBean
    SellPositionRepository sellPositionRepository;

    @MockBean
    PositionOrderService positionOrderService;

    @Autowired
    SellPositionService sellPositionService;

    SellPosition airForcePosition;

    SellPosition sweaterPosition;

    @BeforeEach
    void setup() {
        airForcePosition = new SellPosition(SellPositionTestConstant.AIR_FORCE_POSITION_ID,
                SellPositionTestConstant.POSITION_SIZE_AIR_FORCE, SellPositionTestConstant.POSITION_QUANTITY_AIR_FORCE,
                SellPositionTestConstant.POSITION_ACTIVE_AIR_FORCE, SellPositionTestConstant.POSITION_PRODUCT_AIR_FORCE);

        sweaterPosition = new SellPosition(SellPositionTestConstant.SWEATER_POSITION_ID,
                SellPositionTestConstant.POSITION_SIZE_SWEATER, SellPositionTestConstant.POSITION_QUANTITY_SWEATER,
                SellPositionTestConstant.POSITION_ACTIVE_SWEATER, SellPositionTestConstant.POSITION_PRODUCT_SWEATER);
    }

    @Test
    void getById() {
        SellPosition expected = sweaterPosition;
        doReturn(Optional.of(sweaterPosition)).when(sellPositionRepository).findById(sweaterPosition.getId());

        var actual = sellPositionService.getById(sweaterPosition.getId());

        assertEquals(expected, actual);
        verify(sellPositionRepository).findById(sweaterPosition.getId());
    }

    @Test
    void getAll() {
        SellPositionFilter sellPositionFilter =
                new SellPositionFilter(airForcePosition.getId(), null, null, null);
        Pageable pageable = PageRequest.of(0, 20);
        Page<SellPosition> expected = new PageImpl<>(List.of(airForcePosition));
        doReturn(expected).when(sellPositionRepository).findAll(any(Predicate.class), any(Pageable.class));

        Page<SellPosition> actual = sellPositionService.getAll(sellPositionFilter, pageable);

        assertEquals(expected, actual);
        verify(sellPositionRepository).findAll(any(Predicate.class), any(Pageable.class));
    }

    @Test
    void save() {
        SellPosition expected = new SellPosition();
        doReturn(expected).when(sellPositionRepository).save(expected);

        SellPosition actual = sellPositionService.save(expected);

        assertEquals(expected, actual);
        verify(sellPositionRepository).save(expected);
    }

    @Test
    void update() {
        SellPosition expected = sweaterPosition;
        expected.setSize("Updated");
        doReturn(expected).when(sellPositionRepository).saveAndFlush(expected);

        SellPosition actual = sellPositionService.update(expected);

        assertEquals(expected, actual);
        verify(sellPositionRepository).saveAndFlush(expected);
    }

    @Test
    void subtractQuantityByOrder() {
        Order order = new Order();
        SellPosition updatedSellPosition = airForcePosition;
        PositionOrder positionOrder = new PositionOrder(airForcePosition, order, 1);
        List<PositionOrder> positionOrders = List.of(positionOrder);
        doReturn(positionOrders).when(positionOrderService).getOrderPositionOrderList(order);

        sellPositionService.subtractQuantityByOrder(order);

        assertEquals(airForcePosition, updatedSellPosition);
        verify(sellPositionRepository, times(1)).saveAndFlush(airForcePosition);
    }
}
