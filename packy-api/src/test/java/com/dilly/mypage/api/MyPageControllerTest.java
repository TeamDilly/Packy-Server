package com.dilly.mypage.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.mypage.dto.response.ProfileResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyPageControllerTest extends ControllerTestSupport {

    @DisplayName("나의 프로필을 조회한다.")
    @Test
    @WithCustomMockUser
    void getProfile() throws Exception {
        // given
        ProfileResponse profileResponse = ProfileResponse
            .builder()
            .id(1L)
            .provider("kakao")
            .nickname("테스트")
            .imgUrl("www.test.com")
            .build();

        given(myPageService.getProfile()).willReturn(profileResponse);

        // when // then
        mockMvc.perform(
                get("/api/v1/my-page/profile")
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(1L))
            .andExpect(jsonPath("$.data.provider").value("kakao"))
            .andExpect(jsonPath("$.data.nickname").value("테스트"))
            .andExpect(jsonPath("$.data.imgUrl").value("www.test.com"));
    }
}
