package com.dilly.gift.dto.response;

import com.dilly.gift.Photo;
import lombok.Builder;

@Builder
public record PhotoResponse(
    String photoUrl,
    String description,
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
