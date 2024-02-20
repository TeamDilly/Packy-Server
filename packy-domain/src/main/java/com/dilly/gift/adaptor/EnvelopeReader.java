package com.dilly.gift.adaptor;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.dao.EnvelopeRepository;
import com.dilly.gift.domain.letter.Envelope;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvelopeReader {

	private final EnvelopeRepository envelopeRepository;

	public List<Envelope> findAllByOrderBySequenceAsc() {
		return envelopeRepository.findAllByOrderBySequenceAsc();
	}

	public Envelope findById(Long id) {
		return envelopeRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException(ErrorCode.ENVELOPE_NOT_FOUND)
		);
	}
}
