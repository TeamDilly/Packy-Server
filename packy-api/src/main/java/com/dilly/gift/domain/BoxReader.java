package com.dilly.gift.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.gift.Box;
import com.dilly.gift.dao.BoxRepository;
import com.dilly.global.exception.entitynotfound.EntityNotFoundException;
import com.dilly.global.response.ErrorCode;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoxReader {

	private final BoxRepository boxRepository;

	public List<BoxImgResponse> findAll() {
		return boxRepository.findAll().stream()
			.map(box -> BoxImgResponse.builder()
				.id(box.getId())
				.boxFull(box.getFullImgUrl())
				.boxPart(box.getPartImgUrl())
				.sequence(box.getSequence())
				.build()
			)
			.toList();
	}

	public Box findById(Long id) {
		return boxRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.BOX_NOT_FOUND));
	}
}
