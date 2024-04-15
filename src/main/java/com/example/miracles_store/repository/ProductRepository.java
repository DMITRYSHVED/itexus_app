package com.example.miracles_store.repository;

import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.entity.QProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, QuerydslPredicateExecutor<Product> {

    QProduct qProduct = QProduct.product;

    default Boolean existsByName(String name) {
        return exists(qProduct.name.containsIgnoreCase(name));
    }

    default Boolean existsByNameAndIdNotEqual(String name, Integer id) {
        return exists(qProduct.name.containsIgnoreCase(name).and(qProduct.id.ne(id)));
    }

    default Boolean existsByProductType(ProductType productType) {
        return exists(qProduct.productType.eq(productType));
    }
}
