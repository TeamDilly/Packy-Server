package com.dilly.gift.domain;

import org.springframework.stereotype.Component;

import com.dilly.gift.Letter;
import com.dilly.gift.LetterPaper;
import com.dilly.gift.dao.LetterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LetterWriter {

	private final LetterRepository letterRepository;

	public Letter save(String content, LetterPaper letterPaper) {
		return letterRepository.save(Letter.builder()
			.content(content)
			.letterPaper(letterPaper)
			.build());
	}
}
