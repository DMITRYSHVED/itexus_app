package com.example.miracles_store.service;

import com.example.miracles_store.entity.order.Order;
import com.example.miracles_store.entity.order.PositionOrder;
import com.example.miracles_store.repository.PositionOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PositionOrderService {

    private final PositionOrderRepository positionOrderRepository;

    public void save(PositionOrder positionOrder) {
        positionOrderRepository.save(positionOrder);
    }

    @Transactional(readOnly = true)
    public List<PositionOrder> getOrderPositionOrderList(Order order) {
        return positionOrderRepository.findAllByOrder(order);
    }
}
