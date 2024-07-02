package com.dilly.jwt;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.dilly.global.domain.BaseTimeEntity;
import com.dilly.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String refreshToken;

	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public RefreshToken(Member member, String refreshToken) {
		this.member = member;
		this.refreshToken = refreshToken;
	}

	public void update(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
