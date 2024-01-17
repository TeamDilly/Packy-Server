package com.dilly.admin;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Message {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String imgUrl;
}
