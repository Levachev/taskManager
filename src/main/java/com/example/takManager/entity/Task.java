package com.example.takManager.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String description;
}
