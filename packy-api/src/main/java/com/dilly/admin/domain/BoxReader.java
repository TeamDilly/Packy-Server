package com.dilly.admin.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.dao.BoxRepository;
import com.dilly.admin.dto.response.BoxImgResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoxReader {

	private final BoxRepository boxRepository;

	public List<BoxImgResponse> findAll() {
		return boxRepository.findAll().stream()
			.map(box -> BoxImgResponse.builder()
				.id(box.getId())
				.boxTop(box.getTopImgUrl())
				.boxBottom(box.getBottomImgUrl())
				.build()
			)
			.toList();
	}
}
