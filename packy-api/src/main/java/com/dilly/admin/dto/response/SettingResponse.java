package com.dilly.admin.dto.response;

import com.dilly.admin.domain.Setting;
import lombok.Builder;

@Builder
public record SettingResponse(
    String tag,
    String url
) {

    public static SettingResponse from(Setting setting) {
        return SettingResponse.builder()
            .tag(setting.getSettingTag().toString())
            .url(setting.getUrl())
            .build();
    }
}
