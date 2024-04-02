package com.dilly.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(Include.NON_NULL)
public record StatusResponse(
    @Schema(example = "1")
    Long id,

    @Schema(example = "true")
    Boolean isAvailable,

    @Schema(example = "NEED_UPDATE")
    Reason reason
) {

    public static StatusResponse from(Long id, Boolean isAvailable) {
        return StatusResponse.builder()
            .id(id)
            .isAvailable(isAvailable)
            .build();
    }

    public static StatusResponse from(Long id, Boolean isAvailable, Reason reason) {
        return StatusResponse.builder()
            .id(id)
            .isAvailable(isAvailable)
            .reason(reason)
            .build();
    }
}
