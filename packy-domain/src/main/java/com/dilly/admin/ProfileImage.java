package com.dilly.admin;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String imgUrl;
}
