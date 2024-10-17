package com.dilly.member.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.global.constant.Constants;
import com.dilly.member.domain.Platform;
import com.dilly.member.dto.request.FCMTokenRequest;
import com.dilly.member.dto.response.AppStatusResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

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

    @DisplayName("FCM 토큰을 저장한다.")
    @Test
    @WithCustomMockUser
    void issueFcmToken() throws Exception {
        // given
        FCMTokenRequest fcmTokenRequest = FCMTokenRequest.builder()
            .fcmToken("abc1234")
            .deviceId("ios1234")
            .platform(Platform.IOS.toString())
            .build();

        String successResponse = "FCM 토큰 저장이 완료되었습니다";

        given(memberService.issueFcmToken(fcmTokenRequest))
            .willReturn(successResponse);

        // when // then
        mockMvc.perform(
                post(baseUrl + "/member/fcm-token")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(fcmTokenRequest))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data").value(successResponse));
    }
}
