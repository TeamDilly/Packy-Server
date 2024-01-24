package com.dilly.member;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

import com.dilly.global.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("checkstyle:RegexpSinglelineJava")
@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Provider provider;

	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_USER;

	private String nickname;

	@ManyToOne(fetch = LAZY)
	private ProfileImage profileImg;

	Boolean pushNotification;

	Boolean marketingAgreement;

	@Enumerated(EnumType.STRING)
	Status status = Status.REGISTERED;

	@Builder
	public Member(Provider provider, String nickname, ProfileImage profileImg,
		Boolean pushNotification, Boolean marketingAgreement) {
		this.provider = provider;
		this.nickname = nickname;
		this.profileImg = profileImg;
		this.pushNotification = pushNotification;
		this.marketingAgreement = marketingAgreement;
	}

	public void withdraw() {
		this.status = Status.WITHDRAWAL;
	}
}
