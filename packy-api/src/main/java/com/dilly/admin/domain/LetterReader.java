package com.dilly.admin.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.dao.LetterPaperRepository;
import com.dilly.admin.dto.response.LetterImgResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LetterReader {

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
}
