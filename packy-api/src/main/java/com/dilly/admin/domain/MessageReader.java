package com.dilly.admin.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.dao.MessageRepository;
import com.dilly.admin.dto.response.ImgResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageReader {

	private final MessageRepository messageRepository;

	public List<ImgResponse> findAll() {
		return messageRepository.findAll().stream()
			.map(message -> ImgResponse.builder()
				.id(message.getId())
				.imgUrl(message.getImgUrl())
				.build()
			)
			.toList();
	}
}
