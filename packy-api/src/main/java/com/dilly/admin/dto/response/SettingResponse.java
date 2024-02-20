package com.dilly.admin.dto.response;

import com.dilly.admin.domain.setting.Setting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record SettingResponse(
    @Schema(example = "OFFICIAL_SNS")
    String tag,
    @Schema(example = "www.example.com")
    String url
) {

    public static SettingResponse from(Setting setting) {
        return SettingResponse.builder()
            .tag(setting.getSettingTag().toString())
            .url(setting.getUrl())
            .build();
    }
}
