package com.example.miracles_store.repository;

import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.entity.QProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer>,
        QuerydslPredicateExecutor<ProductType> {

    QProductType qProductType = QProductType.productType;

    default Boolean existsByName(String name) {
        return exists(qProductType.name.containsIgnoreCase(name));
    }

    default Boolean existsByNameAndIdNotEqual(String name, Integer id) {
        return exists(qProductType.name.containsIgnoreCase(name).and(qProductType.id.ne(id)));
    }
}
