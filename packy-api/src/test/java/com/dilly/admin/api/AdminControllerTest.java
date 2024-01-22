package com.dilly.admin.api;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dilly.admin.dto.response.ImgResponse;
import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;

class AdminControllerTest extends ControllerTestSupport {

	@DisplayName("서버 상태를 확인한다.")
	@Test
	@WithCustomMockUser
	void healthCheck() throws Exception {
		// given // when // then
		// TODO: active profile이 null로 뜸
		mockMvc.perform(
				get(baseUrl + "/admin/health")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@DisplayName("프로필 이미지를 조회한다.")
	@Test
	@WithCustomMockUser
	void getProfiles() throws Exception {
		// given
		List<ImgResponse> profiles = List.of(
			ImgResponse.builder().id(1L).imgUrl("www.test1.com").sequence(1L).build(),
			ImgResponse.builder().id(2L).imgUrl("www.test2.com").sequence(2L).build()
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

}
