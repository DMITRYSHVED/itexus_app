package com.example.miracles_store.repository;

import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.entity.QProductType;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer>,
        QuerydslPredicateExecutor<ProductType> {

    QProductType qProductType = QProductType.productType;

    default Boolean existsByName(String name) {
        return exists(qProductType.name.eq(name));
    }

    default Boolean existsByNameAndId(String name, Integer id) {
        BooleanBuilder predicate = new BooleanBuilder(qProductType.name.eq(name));

        predicate.and(qProductType.id.eq(id));
        return exists(predicate);
    }
}
