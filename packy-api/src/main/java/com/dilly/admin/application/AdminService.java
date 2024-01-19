package com.dilly.admin.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.LetterImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.gift.domain.BoxReader;
import com.dilly.gift.domain.LetterPaperReader;
import com.dilly.gift.domain.MessageReader;
import com.dilly.gift.domain.MusicReader;
import com.dilly.member.domain.ProfileImageReader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {

	private final ProfileImageReader profileImageReader;
	private final BoxReader boxReader;
	private final MessageReader messageReader;
	private final LetterPaperReader letterPaperReader;
	private final MusicReader musicReader;

	public List<ImgResponse> getProfiles() {
		return profileImageReader.findAll();
	}

	public List<BoxImgResponse> getBoxes() {
		return boxReader.findAll();
	}

	public List<ImgResponse> getMessages() {
		return messageReader.findAll();
	}

	public List<LetterImgResponse> getLetters() {
		return letterPaperReader.findAll();
	}

	public List<MusicResponse> getMusics() {
		return musicReader.findAll();
	}
}
