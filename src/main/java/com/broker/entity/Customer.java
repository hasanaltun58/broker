package com.broker.entity;



import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    private String userName;

    @Enumerated(EnumType.STRING)
    private Role role; // Role (ADMIN or USER)

}
