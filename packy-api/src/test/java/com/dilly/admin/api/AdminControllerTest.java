package com.dilly.admin.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dilly.admin.dto.response.BoxImgResponse;
import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.admin.dto.response.MusicResponse;
import com.dilly.admin.dto.response.SettingResponse;
import com.dilly.gift.dto.response.EnvelopeListResponse;
import com.dilly.gift.dto.response.EnvelopePaperResponse;
import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.global.fixture.DtoFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junitpioneer.jupiter.RestoreSystemProperties;

class AdminControllerTest extends ControllerTestSupport {

	private final String MEMBER_ID = "1";

	@DisplayName("서버 상태를 확인한다.")
	@ParameterizedTest
	@ValueSource(strings = {"local", "dev", "prod"})
	@RestoreSystemProperties
	@WithCustomMockUser(id = MEMBER_ID)
	void healthCheck(String profile) throws Exception {
		// given
		System.setProperty("spring.profiles.active", profile);
		String expectedData = "This is " + profile + " server!";

		// when // then
		mockMvc.perform(
				get(baseUrl + "/admin/health")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").value(expectedData));
	}

	@DisplayName("프로필 이미지를 조회한다.")
	@Test
	@WithCustomMockUser(id = MEMBER_ID)
	void getProfiles() throws Exception {
		// given
		List<ImgResponse> profiles = List.of(
			DtoFixture.imgResponse(1L, 1L),
			DtoFixture.imgResponse(2L, 2L)
		);

		given(adminService.getProfiles()).willReturn(profiles);

		// when // then
		mockMvc.perform(
				get(baseUrl + "/admin/design/profiles")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data", hasSize(2)));
	}

	@DisplayName("박스 디자인을 조회한다.")
	@Test
	@WithCustomMockUser(id = MEMBER_ID)
	void getBoxes() throws Exception {
		// given
		BoxImgResponse box1 = DtoFixture.boxImgResponse(1L, 1L);
		BoxImgResponse box2 = DtoFixture.boxImgResponse(2L, 2L);
		BoxImgResponse box3 = DtoFixture.boxImgResponse(3L, 3L);
		List<BoxImgResponse> boxes = List.of(box1, box2, box3);

		given(adminService.getBoxes()).willReturn(boxes);

		// when // then
		mockMvc.perform(
				get(baseUrl + "/admin/design/boxes")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data", hasSize(3)));
	}

	@DisplayName("편지 봉투 디자인을 조회한다.")
	@Test
	@WithCustomMockUser(id = MEMBER_ID)
	void getEnvelopes() throws Exception {
		// given
		EnvelopePaperResponse envelopePaper = EnvelopePaperResponse.builder()
			.borderColorCode("ED76A5").opacity(30).build();
		EnvelopePaperResponse letterPaper = EnvelopePaperResponse.builder()
			.borderColorCode("ED76A5").opacity(30).build();

		List<EnvelopeListResponse> envelopes = List.of(
			EnvelopeListResponse.builder().id(1L).imgUrl("www.test1.com").sequence(1L)
				.envelope(envelopePaper).letter(letterPaper).build(),
			EnvelopeListResponse.builder().id(2L).imgUrl("www.test2.com").sequence(2L)
				.envelope(envelopePaper).letter(letterPaper).build(),
			EnvelopeListResponse.builder().id(3L).imgUrl("www.test3.com").sequence(3L)
				.envelope(envelopePaper).letter(letterPaper).build()
		);

		given(adminService.getEnvelopes()).willReturn(envelopes);

		// when // then
		mockMvc.perform(
				get(baseUrl + "/admin/design/envelopes")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data", hasSize(3)));
	}

	@DisplayName("패키 추천 음악을 조회한다.")
	@Test
	@WithCustomMockUser(id = MEMBER_ID)
	void getMusics() throws Exception {
		// given
		List<String> hashtags = List.of("#테스트1", "#테스트2");
		List<MusicResponse> musics = List.of(
			MusicResponse.builder().id(1L).sequence(1L).title("test1").youtubeUrl("www.youtube.com")
				.hashtags(hashtags).build(),
			MusicResponse.builder().id(2L).sequence(2L).title("test2").youtubeUrl("www.youtube.com")
				.hashtags(hashtags).build());

		given(adminService.getMusics()).willReturn(musics);

		// when // then
		mockMvc.perform(
				get(baseUrl + "/admin/music")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data", hasSize(2)));
	}

	@DisplayName("설정 링크를 조회한다.")
	@Test
	@WithCustomMockUser(id = MEMBER_ID)
	void getSettingUrls() throws Exception {
		// given
		List<SettingResponse> settingResponses = List.of(
			SettingResponse.builder().tag("TEST1").url("www.test1.com").build(),
			SettingResponse.builder().tag("TEST2").url("www.test2.com").build(),
			SettingResponse.builder().tag("TEST3").url("www.test3.com").build()
		);

		given(adminService.getSettingUrls()).willReturn(settingResponses);

		// when // then
		mockMvc.perform(
				get(baseUrl + "/admin/settings")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data", hasSize(3)));
	}
}
