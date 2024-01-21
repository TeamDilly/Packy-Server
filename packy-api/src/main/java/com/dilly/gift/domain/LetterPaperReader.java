package com.dilly.gift.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.dto.response.LetterImgResponse;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.LetterPaper;
import com.dilly.gift.dao.LetterPaperRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LetterPaperReader {

	private final LetterPaperRepository letterPaperRepository;

	public List<LetterImgResponse> findAll() {
		return letterPaperRepository.findAll().stream()
			.map(letterPaper -> LetterImgResponse.builder()
				.id(letterPaper.getId())
				.letterPaper(letterPaper.getWritingPaperUrl())
				.envelope(letterPaper.getEnvelopeUrl())
				.build())
			.toList();
	}

	public LetterPaper findById(Long id) {
		return letterPaperRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException(ErrorCode.LETTER_PAPER_NOT_FOUND)
		);
	}
}
