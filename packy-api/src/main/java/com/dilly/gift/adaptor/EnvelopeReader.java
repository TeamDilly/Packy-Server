package com.dilly.gift.adaptor;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.Envelope;
import com.dilly.gift.dao.EnvelopeRepository;
import com.dilly.gift.dto.response.EnvelopeListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvelopeReader {

	private final EnvelopeRepository envelopeRepository;

	public List<EnvelopeListResponse> findAll() {
		return envelopeRepository.findAllByOrderBySequenceAsc().stream()
			.map(envelope -> EnvelopeListResponse.builder()
				.id(envelope.getId())
				.imgUrl(envelope.getImgUrl())
				.sequence(envelope.getSequence())
				.borderColorCode(envelope.getBorderColorCode())
				.build())
			.toList();
	}

	public Envelope findById(Long id) {
		return envelopeRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException(ErrorCode.ENVELOPE_NOT_FOUND)
		);
	}
}
