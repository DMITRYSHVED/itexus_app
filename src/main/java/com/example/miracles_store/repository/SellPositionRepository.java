package com.example.miracles_store.repository;

import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.QSellPosition;
import com.example.miracles_store.entity.SellPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SellPositionRepository extends JpaRepository<SellPosition, Integer>,
        QuerydslPredicateExecutor<SellPosition> {

    QSellPosition qSellPosition = QSellPosition.sellPosition;

    default Boolean existsByProductAndSize(Integer productId, String size) {
        return exists(qSellPosition.product.id.eq(productId).and(qSellPosition.size.containsIgnoreCase(size)));
    }

    default Boolean existsByProductAndSizeAndIdNotEquals(Integer productId, String size, Integer sellPositionId) {
        return exists(qSellPosition.id.ne(sellPositionId)
                .and(qSellPosition.product.id.eq(productId)).and(qSellPosition.size.containsIgnoreCase(size)));
    }

    default Boolean existsByProduct(Product product) {
        return exists(qSellPosition.product.eq(product));
    }
}
