package com.example.miracles_store.repository;

import com.example.miracles_store.entity.Product;
import com.example.miracles_store.entity.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByProductType(ProductType productType);

    Optional<Product> findByName(String name);

    Page<Product> findByIdGreaterThanOrderByIdAsc(int id, Pageable pageable);
}
