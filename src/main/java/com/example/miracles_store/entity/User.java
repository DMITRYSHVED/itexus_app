package com.example.miracles_store.entity;

import com.example.miracles_store.entity.enums.OrderStatus;
import com.example.miracles_store.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
