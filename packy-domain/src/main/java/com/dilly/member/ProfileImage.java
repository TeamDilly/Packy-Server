package com.dilly.member;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class ProfileImage {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String imgUrl;

	private Long sequence;
}
