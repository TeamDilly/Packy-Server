package com.dilly.admin.dto.request;

import lombok.Builder;

@Builder
public record BranchRequest(
    Long boxId
) {

}
