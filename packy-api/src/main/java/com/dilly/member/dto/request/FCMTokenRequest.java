package com.dilly.member.dto.request;

import lombok.Builder;

@Builder
public record FCMTokenRequest(
    String deviceId,
    String platform,
    String fcmToken
) {

}
