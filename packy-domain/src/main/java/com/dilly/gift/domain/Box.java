package com.dilly.gift.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Box {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private Long sequence;

	private String fullImgUrl;

	private String partImgUrl;

	private String bottomImgUrl;
}
