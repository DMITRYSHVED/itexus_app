package com.example.miracles_store.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("orderCart")
public class OrderCart {

    @Id
    private Integer id;

    private List<SellPositionQuantity> sellPositionQuantityList;
}
