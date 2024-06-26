package com.example.miracles_store.repository;

import com.example.miracles_store.entity.order.Order;
import com.example.miracles_store.entity.order.PositionOrder;
import com.example.miracles_store.entity.order.PositionOrderId;
import com.example.miracles_store.entity.order.QPositionOrder;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionOrderRepository extends JpaRepository<PositionOrder, PositionOrderId>, QuerydslPredicateExecutor<PositionOrder> {

    QPositionOrder qPositionOrder = QPositionOrder.positionOrder;

    default List<PositionOrder> findAllByOrder(Order order) {
        return IterableUtils.toList(findAll(qPositionOrder.order.id.eq(order.getId())));
    }
}
