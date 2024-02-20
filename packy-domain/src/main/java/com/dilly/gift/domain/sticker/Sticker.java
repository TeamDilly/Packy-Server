package com.dilly.gift.domain.sticker;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Sticker {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String imgUrl;

    private Long sequence;
}
