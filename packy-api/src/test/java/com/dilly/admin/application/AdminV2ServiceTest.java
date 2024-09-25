package com.dilly.admin.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.dilly.admin.dto.response.SettingV2Response;
import com.dilly.global.IntegrationTestSupport;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdminV2ServiceTest extends IntegrationTestSupport {

    @DisplayName("설정 링크를 조회한다.")
    @Test
    void getSettingUrls() {
        // given
        List<SettingV2Response> settingUrls = settingReader.findAll()
            .stream().map(SettingV2Response::from)
            .toList();

        // when
        List<SettingV2Response> response = adminV2Service.getSettingUrls();

        // then
        assertThat(response).isEqualTo(settingUrls);
    }
}
