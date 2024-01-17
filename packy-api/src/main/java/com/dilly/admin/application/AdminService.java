package com.dilly.admin.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilly.admin.domain.BoxReader;
import com.dilly.admin.domain.LetterReader;
import com.dilly.admin.domain.MessageReader;
import com.dilly.admin.domain.MusicReader;
import com.dilly.admin.domain.ProfileImageReader;
import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.LetterImgResponse;
import com.dilly.admin.dto.response.MusicResponse;

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
	private final LetterReader letterReader;
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
		return letterReader.findAll();
	}

	public List<MusicResponse> getMusics() {
		return musicReader.findAll();
	}
}
