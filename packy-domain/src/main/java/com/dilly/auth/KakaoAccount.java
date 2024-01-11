package com.dilly.auth;

import com.dilly.global.BaseTimeEntity;
import com.dilly.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class KakaoAccount extends BaseTimeEntity {

	@Id
	private String id;

	@OneToOne
	@JoinColumn
	private Member member;

	@Builder
	public KakaoAccount(String id, Member member) {
		this.id = id;
		this.member = member;
	}
}
