package com.example.miracles_store.repository;

import com.example.miracles_store.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

    Optional <ProductType> findByName (String name);
}
