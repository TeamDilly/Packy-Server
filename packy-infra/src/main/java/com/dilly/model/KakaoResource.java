package com.dilly.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonInclude(NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoResource {

	private String id;
	private KakaoAccountInfo kakaoAccount;

	@JsonInclude(NON_NULL)
	public static class KakaoAccountInfo {

		private Profile profile;
		private String email;
		private String gender;

		@JsonInclude(NON_NULL)
		@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
		public static class Profile {

			private String nickname;
			private String profileImageUrl;
		}
	}

}
