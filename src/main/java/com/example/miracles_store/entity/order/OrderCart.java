package com.example.miracles_store.entity.order;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("orderCart")
public class OrderCart {

    @Id
    private Integer id;

    private Set<SellPositionQuantity> sellPositionQuantitySet;
}
