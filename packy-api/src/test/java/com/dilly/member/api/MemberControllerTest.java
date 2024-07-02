package com.dilly.member.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.global.constant.Constants;
import com.dilly.member.dto.response.AppStatusResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberControllerTest extends ControllerTestSupport {


    @DisplayName("앱 사용 가능 상태를 확인한다.")
    @Test
    @WithCustomMockUser
    void getStatus() throws Exception {
        // given
        AppStatusResponse appStatusResponse = AppStatusResponse.builder()
            .id(1L)
            .isAvailable(true)
            .build();

        given(memberService.getStatus(Constants.MINIMUM_REQUIRED_VERSION))
            .willReturn(appStatusResponse);

        // when // then
        mockMvc.perform(
                get(baseUrl + "/member/status")
                    .param("app-version", Constants.MINIMUM_REQUIRED_VERSION)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.isAvailable").value(true));
    }
}
