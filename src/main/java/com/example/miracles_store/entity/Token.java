package com.example.miracles_store.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("token")
public class Token {

    @Id
    private String id;

    @TimeToLive
    private Long expiration;
}