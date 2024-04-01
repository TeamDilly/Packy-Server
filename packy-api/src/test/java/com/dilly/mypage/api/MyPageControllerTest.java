package com.dilly.mypage.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.member.dto.request.ProfileRequest;
import com.dilly.member.dto.response.ProfileResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

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

    @Nested
    @DisplayName("나의 프로필을 수정한다.")
    class updateProfile {

        private final String newNickname = "닉네임변경";

        @DisplayName("닉네임과 프로필 이미지를 수정한다.")
        @Test
        @WithCustomMockUser
        void updateNicknameAndProfileImage() throws Exception {
            // given
            ProfileRequest profileRequest = ProfileRequest.builder()
                .nickname(newNickname)
                .profileImg(2L)
                .build();

            given(myPageService.updateProfile(profileRequest)).willReturn(ProfileResponse.builder()
                .id(1L)
                .provider("test")
                .nickname(newNickname)
                .imgUrl("www.example2.com")
                .build());

            // when // then
            mockMvc.perform(
                    patch("/api/v1/my-page/profile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").value(newNickname))
                .andExpect(jsonPath("$.data.imgUrl").value("www.example2.com"));
        }

        @DisplayName("닉네임만 수정한다.")
        @Test
        @WithCustomMockUser
        void updateNickname() throws Exception {
            // given
            ProfileRequest profileRequest = ProfileRequest.builder()
                .nickname(newNickname)
                .build();

            given(myPageService.updateProfile(profileRequest)).willReturn(ProfileResponse.builder()
                .id(1L)
                .provider("test")
                .nickname(newNickname)
                .imgUrl("www.example1.com")
                .build());

            // when // then
            mockMvc.perform(
                    patch("/api/v1/my-page/profile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").value(newNickname))
                .andExpect(jsonPath("$.data.imgUrl").value("www.example1.com"));
        }

        @DisplayName("프로필 이미지만 수정한다.")
        @Test
        @WithCustomMockUser
        void updateProfileImage() throws Exception {
            // given
            ProfileRequest profileRequest = ProfileRequest.builder()
                .profileImg(2L)
                .build();

            given(myPageService.updateProfile(profileRequest)).willReturn(ProfileResponse.builder()
                .id(1L)
                .provider("test")
                .nickname("1번유저")
                .imgUrl("www.example2.com")
                .build());

            // when // then
            mockMvc.perform(
                    patch("/api/v1/my-page/profile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nickname").value("1번유저"))
                .andExpect(jsonPath("$.data.imgUrl").value("www.example2.com"));
        }
    }
}
