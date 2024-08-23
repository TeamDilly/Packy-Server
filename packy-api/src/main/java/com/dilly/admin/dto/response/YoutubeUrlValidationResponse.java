package com.dilly.admin.dto.response;

import lombok.Builder;

@Builder
public record YoutubeUrlValidationResponse(
    boolean status
) {

    public static YoutubeUrlValidationResponse from(boolean status) {
        return YoutubeUrlValidationResponse.builder()
            .status(status)
            .build();
    }
}
