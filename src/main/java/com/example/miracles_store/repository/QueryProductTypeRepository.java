package com.example.miracles_store.repository;

import com.example.miracles_store.entity.ProductType;
import com.example.miracles_store.entity.QProductType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QueryProductTypeRepository {

    private final EntityManager entityManager;

    public Page<ProductType> findAllPage(Integer id, Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QProductType productType = QProductType.productType;
        long total = queryFactory.selectFrom(productType).where(productType.id.gt(id)).fetchCount();

        List<ProductType> productTypes = queryFactory.selectFrom(productType)
                .where(productType.id.gt(id))
                .orderBy(productType.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(productTypes, pageable, total);
    }
}
