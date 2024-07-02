package com.dilly.auth.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.dilly.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(NON_NULL)
public class KakaoResource {

	private String id;
	private KakaoAccount kakao_account;

    public com.dilly.auth.domain.KakaoAccount toMember(Member member) {
        return com.dilly.auth.domain.KakaoAccount.builder()
			.id(id)
			.member(member)
			.build();
	}

	@JsonInclude(NON_NULL)
	public static class KakaoAccount {

		private Profile profile;
		private String email;
		private String gender;

		@JsonInclude(NON_NULL)
		public static class Profile {

			private String nickname;
			private String profile_image_url;
		}
	}

}
