package com.dilly.admin.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.dao.LetterRepository;
import com.dilly.admin.dto.response.LetterImgResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LetterReader {

	private final LetterRepository letterRepository;

	public List<LetterImgResponse> findAll() {
		return letterRepository.findAll().stream()
			.map(letter -> LetterImgResponse.builder()
				.id(letter.getId())
				.letterPaper(letter.getLetterPaper().getWritingPaperUrl())
				.envelope(letter.getLetterPaper().getEnvelopeUrl())
				.build()
			)
			.toList();
	}
}
