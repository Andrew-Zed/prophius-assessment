package com.andrew.prophiusassessment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Content cannot be empty")
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

}