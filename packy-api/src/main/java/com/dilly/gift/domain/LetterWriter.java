package com.dilly.gift.domain;

import org.springframework.stereotype.Component;

import com.dilly.gift.Envelope;
import com.dilly.gift.Letter;
import com.dilly.gift.dao.LetterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LetterWriter {

	private final LetterRepository letterRepository;

	public Letter save(String content, Envelope envelope) {
		return letterRepository.save(Letter.builder()
			.content(content)
			.envelope(envelope)
			.build());
	}
}
