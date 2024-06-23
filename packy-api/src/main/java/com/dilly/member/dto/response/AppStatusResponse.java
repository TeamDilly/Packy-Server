package com.dilly.member.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(Include.NON_NULL)
public record AppStatusResponse(
    @Schema(example = "1")
    Long id,

    @Schema(example = "true")
    Boolean isAvailable,

    @Schema(example = "NEED_UPDATE")
    Reason reason
) {

    public static AppStatusResponse from(Long id, Boolean isAvailable) {
        return AppStatusResponse.builder()
            .id(id)
            .isAvailable(isAvailable)
            .build();
    }

    public static AppStatusResponse from(Long id, Boolean isAvailable, Reason reason) {
        return AppStatusResponse.builder()
            .id(id)
            .isAvailable(isAvailable)
            .reason(reason)
            .build();
    }
}
