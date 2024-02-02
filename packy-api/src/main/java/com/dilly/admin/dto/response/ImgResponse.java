package com.dilly.admin.dto.response;

import com.dilly.gift.Sticker;
import com.dilly.member.ProfileImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ImgResponse(
	@Schema(example = "1")
	Long id,
	@Schema(example = "1")
	Long sequence,
	@Schema(example = "www.example.com")
	String imgUrl
) {

	public static ImgResponse of(ProfileImage profileImage) {
		return ImgResponse.builder()
			.id(profileImage.getId())
			.sequence(profileImage.getSequence())
			.imgUrl(profileImage.getImgUrl())
			.build();
	}

	public static ImgResponse of(Sticker sticker) {
		return ImgResponse.builder()
			.id(sticker.getId())
			.sequence(sticker.getSequence())
			.imgUrl(sticker.getImgUrl())
			.build();
	}
}
