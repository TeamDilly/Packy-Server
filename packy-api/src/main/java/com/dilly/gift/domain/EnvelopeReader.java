package com.dilly.gift.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.Envelope;
import com.dilly.gift.dao.EnvelopeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EnvelopeReader {

	private final EnvelopeRepository envelopeRepository;

	public List<ImgResponse> findAll() {
		return envelopeRepository.findAll().stream()
			.map(envelope -> ImgResponse.builder()
				.id(envelope.getId())
				.imgUrl(envelope.getImgUrl())
				.sequence(envelope.getSequence())
				.build())
			.toList();
	}

	public Envelope findById(Long id) {
		return envelopeRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException(ErrorCode.LETTER_PAPER_NOT_FOUND)
		);
	}
}
