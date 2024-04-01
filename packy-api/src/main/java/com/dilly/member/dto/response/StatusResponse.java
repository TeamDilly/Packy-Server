package com.dilly.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(Include.NON_NULL)
public record StatusResponse(
    @Schema(example = "true")
    Boolean isAvailable,

    @Schema(example = "NEED_UPDATE")
    Reason reason
) {

    public static StatusResponse from(Boolean isAvailable) {
        return StatusResponse.builder()
            .isAvailable(isAvailable)
            .build();
    }

    public static StatusResponse from(Boolean isAvailable, Reason reason) {
        return StatusResponse.builder()
            .isAvailable(isAvailable)
            .reason(reason)
            .build();
    }
}
