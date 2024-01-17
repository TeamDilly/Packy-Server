package com.dilly.admin.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
				get("/api/v1/admin/health")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
}
