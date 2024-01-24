package com.dilly.gift;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Envelope {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private Long sequence;

	private String imgUrl;
}
