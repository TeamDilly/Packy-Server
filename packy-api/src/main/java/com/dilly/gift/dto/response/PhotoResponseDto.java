package com.dilly.gift.dto.response;

import com.dilly.gift.domain.Photo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public class PhotoResponseDto {

    @Builder
    public static record PhotoResponse(
        @Schema(example = "www.example.com")
        String photoUrl,
        @Schema(example = "우리 같이 트리 만든 날 :)")
        String description,
        @Schema(example = "1")
        Integer sequence
    ) {

        public static PhotoResponse of(Photo photo) {
            return PhotoResponse.builder()
                .photoUrl(photo.getImgUrl())
                .description(photo.getDescription())
                .sequence(photo.getSequence())
                .build();
        }
    }

    @Builder
    public static record PhotoWithoutSequenceResponse(
        @Schema(example = "www.example.com")
        String photoUrl,
        @Schema(example = "우리 같이 트리 만든 날 :)")
        String description
    ) {

        public static PhotoWithoutSequenceResponse of(Photo photo) {
            return PhotoWithoutSequenceResponse.builder()
                .photoUrl(photo.getImgUrl())
                .description(photo.getDescription())
                .build();
        }
    }
}
