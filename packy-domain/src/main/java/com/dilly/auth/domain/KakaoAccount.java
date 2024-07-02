package com.dilly.auth.domain;

import com.dilly.global.domain.BaseTimeEntity;
import com.dilly.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
