package com.example.miracles_store.repository;

import com.example.miracles_store.entity.OrderCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCartRepository extends CrudRepository<OrderCart, Integer> {
}
