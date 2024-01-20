package com.dilly.auth;

import com.dilly.global.BaseTimeEntity;
import com.dilly.member.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppleAccount extends BaseTimeEntity {

	@Id
	private String id;

	@Column(nullable = false)
	private String refreshToken;

	@OneToOne
	@JoinColumn
	private Member member;
}
