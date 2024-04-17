package com.example.miracles_store.repository;

import com.example.miracles_store.entity.QToken;
import com.example.miracles_store.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>, QuerydslPredicateExecutor<Token> {

    QToken qToken = QToken.token1;

    default Boolean existsByToken(String token) {
        return exists(qToken.token.eq(token));
    }
}
