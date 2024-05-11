package com.example.miracles_store.service;

import com.example.miracles_store.dto.filter.OrderFilter;
import com.example.miracles_store.entity.order.Order;
import com.example.miracles_store.entity.order.PositionOrder;
import com.example.miracles_store.entity.order.QOrder;
import com.example.miracles_store.entity.enums.OrderStatus;
import com.example.miracles_store.entity.order.SellPositionQuantity;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.repository.OrderRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final PositionOrderService positionOrderService;

    private final OrderCartService orderCartService;

    private final SellPositionService sellPositionService;

    @Transactional(readOnly = true)
    public Page<Order> getAll(OrderFilter filter, Pageable pageable) {
        QOrder qOrder = QOrder.order;
        BooleanBuilder predicate = new BooleanBuilder();

        if (filter.sum() != null) {
            predicate.and(qOrder.sum.eq(filter.sum()));
        }
        if (filter.orderStatus() != null) {
            OrderStatus status = OrderStatus.valueOf(filter.orderStatus());
            predicate.and(qOrder.orderStatus.eq(status));
        }
        if (filter.addressId() != null) {
            predicate.and(qOrder.address.id.eq(filter.addressId()));
        }
        if (filter.userId() != null) {
            predicate.and(qOrder.user.id.eq(filter.userId()));
        }
        return orderRepository.findAll(predicate, pageable);
    }

    public Order saveOrder(Order order, Set<SellPositionQuantity> sellPositionQuantitySet) {
        orderRepository.save(order);

        sellPositionQuantitySet.stream()
                .map(sellPositionQuantity -> {
                    PositionOrder positionOrder = new PositionOrder();
                    positionOrder.setOrder(order);
                    positionOrder.setQuantity(sellPositionQuantity.getQuantity());
                    positionOrder.setSellPosition(sellPositionService.getById(sellPositionQuantity.getSellPositionId()));
                    return positionOrder;
                })
                .forEach(positionOrderService::save);
        sellPositionService.subtractQuantityByOrder(order);
        orderCartService.deleteById(order.getUser().getId());
        return order;
    }

    @Transactional(readOnly = true)
    public Order getById(Integer id) {
        return orderRepository.
                findById(id).orElseThrow(() -> new ObjectNotFoundException("Can't find order with id: " + id));
    }

    public Order update(Order order) {
        return orderRepository.saveAndFlush(order);
    }
}
