package com.example.miracles_store.service;

import com.example.miracles_store.entity.Order;
import com.example.miracles_store.entity.PositionOrder;
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

    public void saveAll(List<PositionOrder> positionOrders) {
        positionOrderRepository.saveAll(positionOrders);
    }

    @Transactional(readOnly = true)
    public List<PositionOrder> getOrderPositionOrderList(Order order) {
        return positionOrderRepository.findAllByOrder(order);
    }
}
