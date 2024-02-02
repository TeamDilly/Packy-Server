package com.dilly.gift.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

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
public class Letter {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	private Envelope envelope;

	public static Letter of(String content, Envelope envelope) {
		return Letter.builder()
			.content(content)
			.envelope(envelope)
			.build();
	}
}
