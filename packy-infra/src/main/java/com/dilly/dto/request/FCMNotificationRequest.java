package com.dilly.dto.request;

import lombok.Builder;

@Builder
public record FCMNotificationRequest(
    Long targetId,
    String title,
    String body
) {
}
