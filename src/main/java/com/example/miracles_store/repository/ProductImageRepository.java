package com.example.miracles_store.repository;

import com.example.miracles_store.entity.ProductImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends MongoRepository<ProductImage, String> {
}
