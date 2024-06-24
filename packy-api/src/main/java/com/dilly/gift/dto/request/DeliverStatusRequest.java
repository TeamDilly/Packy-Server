package com.dilly.gift.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record DeliverStatusRequest(
    @Schema(example = "DELIVERED")
    String deliverStatus
) {

}
