package com.dilly.gift.domain.letter;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Envelope {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private Long sequence;

	@Embedded
	private EnvelopePaper envelopePaper;

	@Embedded
	private LetterPaper letterPaper;

	private String imgUrl;
}
