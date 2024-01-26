package com.dilly.gift.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.GiftRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.request.StickerRequest;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class GiftControllerTest extends ControllerTestSupport {

    @DisplayName("선물박스를 만든다.")
    @Test
    @WithCustomMockUser
    void createGiftBox() throws Exception {
        // given
        GiftBoxRequest giftBoxRequest = GiftBoxRequest.builder()
            .name("test")
            .senderName("sender")
            .receiverName("receiver")
            .boxId(1L)
            .envelopeId(1L)
            .letterContent("This is letter content.")
            .youtubeUrl("www.youtube.com")
            .photos(List.of(
                PhotoRequest.builder().photoUrl("www.test1.com").description("description1")
                    .build(),
                PhotoRequest.builder().photoUrl("www.test2.com").description("description2").build()
            ))
            .stickers(List.of(
                StickerRequest.builder().id(1L).location(1).build(),
                StickerRequest.builder().id(2L).location(2).build())
            )
            .gift(GiftRequest.builder()
                .type("photo")
                .url("www.naver.com")
                .build()
            )
            .build();
        GiftBoxResponse giftBoxResponse = GiftBoxResponse.builder()
            .id(1L)
            .uuid("550e8400-e29b-41d4-a716-446655440000")
            .build();

        given(giftService.createGiftBox(giftBoxRequest)).willReturn(giftBoxResponse);

        // when // then
        mockMvc.perform(
                post(baseUrl + "/giftbox")
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
