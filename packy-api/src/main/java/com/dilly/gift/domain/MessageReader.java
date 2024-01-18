package com.dilly.gift.domain;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.gift.Message;
import com.dilly.gift.dao.MessageRepository;
import com.dilly.global.exception.entitynotfound.EntityNotFoundException;
import com.dilly.global.response.ErrorCode;

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

	public Message findById(Long id) {
		return messageRepository.findById(id).orElseThrow(
			() -> new EntityNotFoundException(ErrorCode.MESSAGE_NOT_FOUND)
		);
	}
}
