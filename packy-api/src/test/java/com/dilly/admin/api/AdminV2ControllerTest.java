package com.dilly.admin.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dilly.admin.dto.response.SettingV2Response;
import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdminV2ControllerTest extends ControllerTestSupport {

    @DisplayName("설정 링크를 조회한다.")
    @Test
    @WithCustomMockUser
    void getSettingUrls() throws Exception {
        // given
        List<SettingV2Response> settingV2Responses = List.of(
            SettingV2Response.builder().name("테스트 1").url("www.test1.com").build(),
            SettingV2Response.builder().name("테스트 2").url("www.test2.com").build(),
            SettingV2Response.builder().name("테스트 3").url("www.test3.com").build()
        );

        given(adminV2Service.getSettingUrls()).willReturn(settingV2Responses);

        // when // then
        mockMvc.perform(
            get(baseUrlV2 + "/admin/settings")
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data", hasSize(3)));
    }
}
