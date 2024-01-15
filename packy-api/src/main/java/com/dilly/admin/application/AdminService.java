package com.dilly.admin.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dilly.admin.ProfileImageReader;
import com.dilly.admin.dto.response.ImgResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {

	private final ProfileImageReader profileImageReader;

	public List<ImgResponse> getProfiles() {
		return profileImageReader.findAll();
	}
}
