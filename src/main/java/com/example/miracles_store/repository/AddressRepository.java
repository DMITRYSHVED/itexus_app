package com.example.miracles_store.repository;

import com.example.miracles_store.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>, QuerydslPredicateExecutor<Address> {
}
