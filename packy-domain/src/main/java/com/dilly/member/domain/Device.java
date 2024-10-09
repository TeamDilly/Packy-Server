package com.dilly.member.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String token;

    @ManyToOne(fetch = LAZY)
    private Member member;
}
