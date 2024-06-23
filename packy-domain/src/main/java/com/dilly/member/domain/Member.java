package com.dilly.member.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.dilly.global.domain.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@SuppressWarnings("checkstyle:RegexpSinglelineJava")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateProfileImage(ProfileImage profileImg) {
		this.profileImg = profileImg;
	}
}
