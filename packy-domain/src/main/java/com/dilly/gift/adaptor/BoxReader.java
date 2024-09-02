package com.dilly.gift.adaptor;

import com.dilly.exception.EntityNotFoundException;
import com.dilly.exception.ErrorCode;
import com.dilly.gift.dao.BoxRepository;
import com.dilly.gift.domain.Box;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoxReader {

	private final BoxRepository boxRepository;

	public List<Box> findAll() {
		return boxRepository.findAll();
	}

	public List<Box> findAllByOrderBySequenceAsc() {
		return boxRepository.findAllByOrderBySequenceAsc();
	}

	public Box findById(Long id) {
		return boxRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.BOX_NOT_FOUND));
	}
}
