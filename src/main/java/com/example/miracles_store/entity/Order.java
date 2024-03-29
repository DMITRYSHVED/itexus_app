package com.example.miracles_store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String address;

    @Column
    private String phone;

    @Column(name = "order_comment")
    private String orderComment;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus order_status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
