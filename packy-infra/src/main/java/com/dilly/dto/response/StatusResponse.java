package com.dilly.dto.response;

import lombok.Builder;

@Builder
public record StatusResponse(
    boolean status
) {

    public static StatusResponse from(boolean status) {
        return StatusResponse.builder()
            .status(status)
            .build();
    }
}
