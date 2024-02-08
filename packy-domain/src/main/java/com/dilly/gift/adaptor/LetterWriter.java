package com.dilly.gift.adaptor;

import com.dilly.gift.dao.LetterRepository;
import com.dilly.gift.domain.Envelope;
import com.dilly.gift.domain.Letter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LetterWriter {

	private final LetterRepository letterRepository;

	public Letter save(String content, Envelope envelope) {
		return letterRepository.save(Letter.of(content, envelope));
	}
}
