package com.dilly.admin.domain.notice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class NoticeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;

    private Long sequence;

    @ManyToOne
    private Notice notice;
}
