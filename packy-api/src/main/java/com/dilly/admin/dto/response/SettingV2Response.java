package com.dilly.admin.dto.response;

import com.dilly.admin.domain.setting.Setting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record SettingV2Response(
    @Schema(example = "패키 공식 SNS")
    String name,
    @Schema(example = "www.example.com")
    String url
) {

    public static SettingV2Response from(Setting setting) {
        return SettingV2Response.builder()
            .name(setting.getName())
            .url(setting.getUrl())
            .build();
    }
}
