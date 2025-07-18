package com.chiikawa.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data // Include getter and setter methods
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private Integer age;
    private String address;
    private String role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
