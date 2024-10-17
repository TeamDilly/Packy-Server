package com.dilly.dto.request;

import lombok.Builder;

@Builder
public record FCMNotificationRequest(
    String title,
    String body
) {
}
