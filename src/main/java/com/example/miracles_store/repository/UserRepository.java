package com.example.miracles_store.repository;

import com.example.miracles_store.entity.QUser;
import com.example.miracles_store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User> {

    QUser qUser = QUser.user;

    default Optional<User> findByEmail(String email) {
        return findOne(qUser.email.containsIgnoreCase(email));
    }
}
