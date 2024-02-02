package com.dilly.auth.domain;

import com.dilly.global.BaseTimeEntity;
import com.dilly.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
