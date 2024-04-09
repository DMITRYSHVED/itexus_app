package com.example.miracles_store.repository;

import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, QuerydslPredicateExecutor<Product> {

    Optional<Product> findByName(String name);

    Boolean existsByProductType (ProductType productType);

    Boolean existsByName (String name);
}
