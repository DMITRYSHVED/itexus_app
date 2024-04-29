package com.example.miracles_store.service;

import com.example.miracles_store.dto.filter.OrderFilter;
import com.example.miracles_store.entity.Order;
import com.example.miracles_store.entity.OrderCart;
import com.example.miracles_store.entity.PositionOrder;
import com.example.miracles_store.entity.QOrder;
import com.example.miracles_store.entity.SellPosition;
import com.example.miracles_store.entity.SellPositionQuantity;
import com.example.miracles_store.entity.enums.OrderStatus;
import com.example.miracles_store.exception.EmptyOrderCartException;
import com.example.miracles_store.exception.NotActivePositionException;
import com.example.miracles_store.exception.ObjectNotFoundException;
import com.example.miracles_store.exception.OutOfStockException;
import com.example.miracles_store.repository.OrderRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public Order saveOrder(Order order) {
        List<PositionOrder> positionOrdersToSave = new ArrayList<>();
        OrderCart orderCart = orderCartService.getByUserId(order.getUser().getId());
        List<SellPositionQuantity> sellPositionQuantityList = orderCart.getSellPositionQuantityList();

        if (sellPositionQuantityList.isEmpty()) {
            throw new EmptyOrderCartException("The order cart is empty");
        }
        for (SellPositionQuantity sellPositionQuantity : sellPositionQuantityList) {
            SellPosition sellPosition = sellPositionService.getById(sellPositionQuantity.getSellPositionId());
            PositionOrder positionOrder;
            if (!sellPosition.getIsActive()) {
                throw new NotActivePositionException(String.
                        format("SellPosition %s is not active.", sellPosition.getProduct().getName()));
            }
            if (sellPosition.getQuantity() < sellPositionQuantity.getQuantity()) {
                throw new OutOfStockException("There is not enough stock of " + sellPosition.getProduct().getName());
            }
            positionOrder = new PositionOrder();
            positionOrder.setSellPosition(sellPosition);
            positionOrder.setOrder(order);
            positionOrder.setQuantity(sellPositionQuantity.getQuantity());
            positionOrdersToSave.add(positionOrder);
        }
        orderCartService.deleteSellPositionFromCart(orderCart);
        orderRepository.save(order);
        positionOrderService.saveAll(positionOrdersToSave);
        sellPositionService.subtractQuantityByOrder(order);
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
