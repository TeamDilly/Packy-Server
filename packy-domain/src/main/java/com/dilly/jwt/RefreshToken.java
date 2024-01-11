package com.dilly.jwt;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 7)
@Getter
@NoArgsConstructor
public class RefreshToken {

	@Id
	private Long memberId;

	private String refreshToken;

	@Builder
	public RefreshToken(Long memberId, String refreshToken) {
		this.memberId = memberId;
		this.refreshToken = refreshToken;
	}
}
