package com.dilly.gift.api;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;

class GiftControllerTest extends ControllerTestSupport {

	@DisplayName("선물박스를 만든다.")
	@Test
	@WithCustomMockUser
	void createGiftBox() throws Exception {
		// given
		GiftBoxRequest giftBoxRequest = GiftBoxRequest.builder()
			.boxId(1L)
			.messageId(1L)
			.letterPaperId(1L)
			.letterContent("This is letter content.")
			.youtubeUrl("www.youtube.com")
			.photos(List.of(
				PhotoRequest.builder().photoUrl("www.test1.com").description("description1").build(),
				PhotoRequest.builder().photoUrl("www.test2.com").description("description2").build()
			))
			.giftType("photo")
			.giftUrl("www.naver.com")
			.giftMessage("This is gift message.")
			.build();
		GiftBoxResponse giftBoxResponse = GiftBoxResponse.builder()
			.id(1L)
			.uuid("550e8400-e29b-41d4-a716-446655440000")
			.build();

		given(giftService.createGiftBox(giftBoxRequest)).willReturn(giftBoxResponse);

		// when // then
		mockMvc.perform(
				post(BASE_URL + "/giftbox")
					.with(csrf())
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(giftBoxRequest))
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.id").isNumber())
			.andExpect(jsonPath("$.data.uuid").isString());
	}
}
