package com.dilly.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(NON_NULL)
public class KakaoResource {

	private String id;
	private KakaoAccountInfo kakao_account;

	@JsonInclude(NON_NULL)
	public static class KakaoAccountInfo {

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
