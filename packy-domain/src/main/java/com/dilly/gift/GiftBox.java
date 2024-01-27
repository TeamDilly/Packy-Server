package com.dilly.gift;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.dilly.global.BaseTimeEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftBox extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String uuid;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Box box;

    @ManyToOne(fetch = FetchType.LAZY)
    private Letter letter;

    private String youtubeUrl;

    @Embedded
    private Gift gift;
}
