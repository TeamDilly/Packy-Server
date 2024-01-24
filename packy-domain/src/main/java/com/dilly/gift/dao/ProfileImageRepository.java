package com.dilly.gift.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dilly.member.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
