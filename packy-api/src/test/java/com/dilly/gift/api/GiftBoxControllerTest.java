package com.dilly.gift.api;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.GiftRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.request.StickerRequest;
import com.dilly.gift.dto.response.BoxResponse;
import com.dilly.gift.dto.response.EnvelopeResponse;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftResponse;
import com.dilly.gift.dto.response.PhotoResponse;
import com.dilly.gift.dto.response.StickerResponse;
import com.dilly.global.ControllerTestSupport;
import com.dilly.global.WithCustomMockUser;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.MediaType;

class GiftBoxControllerTest extends ControllerTestSupport {

    String giftBoxBaseUrl = baseUrl + "/giftboxes";

    @DisplayName("선물박스 만들기 시나리오")
    @TestFactory
    @WithCustomMockUser
    Collection<DynamicTest> createGiftBox() {
        // given
        List<PhotoRequest> photos = Collections.singletonList(PhotoRequest.builder()
            .photoUrl("www.test1.com").description("description1").sequence(1)
            .build());
        List<StickerRequest> stickers = List.of(
            StickerRequest.builder().id(1L).location(1).build(),
            StickerRequest.builder().id(2L).location(2).build());
        GiftBoxIdResponse giftBoxIdResponse = GiftBoxIdResponse.builder()
            .id(1L)
            .uuid("550e8400-e29b-41d4-a716-446655440000")
            .build();

        return List.of(
            DynamicTest.dynamicTest("선물이 있을 경우", () -> {
                //given
                GiftBoxRequest giftBoxRequest = GiftBoxRequest.builder()
                    .name("test")
                    .senderName("보내는사람")
                    .receiverName("받는사람")
                    .boxId(1L)
                    .envelopeId(1L)
                    .letterContent("This is letter content.")
                    .youtubeUrl("www.youtube.com")
                    .photos(photos)
                    .stickers(stickers)
                    .gift(GiftRequest.builder()
                        .type("photo")
                        .url("www.naver.com")
                        .build()
                    )
                    .build();

                given(giftBoxService.createGiftBox(giftBoxRequest)).willReturn(giftBoxIdResponse);

                // when // then
                mockMvc.perform(
                        post(giftBoxBaseUrl)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(giftBoxRequest))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").isNumber())
                    .andExpect(jsonPath("$.data.uuid").isString());
            }),
            DynamicTest.dynamicTest("선물이 없을 경우", () -> {
                //given
                GiftBoxRequest giftBoxRequest = GiftBoxRequest.builder()
                    .name("test")
                    .senderName("보내는사람")
                    .receiverName("받는사람")
                    .boxId(1L)
                    .envelopeId(1L)
                    .letterContent("This is letter content.")
                    .youtubeUrl("www.youtube.com")
                    .photos(photos)
                    .stickers(stickers)
                    .gift(null)
                    .build();

                given(giftBoxService.createGiftBox(giftBoxRequest)).willReturn(giftBoxIdResponse);

                // when // then
                mockMvc.perform(
                        post(giftBoxBaseUrl)
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(giftBoxRequest))
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.id").isNumber())
                    .andExpect(jsonPath("$.data.uuid").isString());
            })
        );
    }

    @DisplayName("선물박스 열기 시나리오")
    @TestFactory
    @WithCustomMockUser
    Collection<DynamicTest> openGiftBox() {
        // given
        BoxResponse boxResponse = BoxResponse.builder()
            .id(1L)
            .boxNormal("www.example.com")
            .build();
        EnvelopeResponse envelopeResponse = EnvelopeResponse.builder()
            .imgUrl("www.example.com")
            .borderColorCode("000000")
            .build();
        List<PhotoResponse> photos = Collections.singletonList(PhotoResponse.builder()
            .photoUrl("www.example.com")
            .description("description")
            .sequence(1)
            .build());
        List<StickerResponse> stickers = List.of(
            StickerResponse.builder().imgUrl("www.example.com").location(1).build(),
            StickerResponse.builder().imgUrl("www.example.com").location(2).build());
        GiftResponse giftResponse = GiftResponse.builder()
            .type("photo")
            .url("www.naver.com")
            .build();

        return List.of(
            DynamicTest.dynamicTest("선물이 있을 경우", () -> {
                // given
                GiftBoxResponse giftBoxResponse = GiftBoxResponse.builder()
                    .name("test")
                    .senderName("sender")
                    .receiverName("receiver")
                    .box(boxResponse)
                    .envelope(envelopeResponse)
                    .letterContent("This is letter content.")
                    .youtubeUrl("www.youtube.com")
                    .photos(photos)
                    .stickers(stickers)
                    .gift(giftResponse)
                    .build();
                given(giftBoxService.openGiftBox(anyLong())).willReturn(giftBoxResponse);

                // when // then
                mockMvc.perform(
                        get(giftBoxBaseUrl + "/{giftBoxId}", anyLong())
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.name").value(giftBoxResponse.name()))
                    .andExpect(jsonPath("$.data.senderName").value(giftBoxResponse.senderName()))
                    .andExpect(
                        jsonPath("$.data.receiverName").value(giftBoxResponse.receiverName()))
                    .andExpect(
                        jsonPath("$.data.box.id").value(giftBoxResponse.box().id()))
                    .andExpect(
                        jsonPath("$.data.box.boxNormal").value(giftBoxResponse.box().boxNormal()))
                    .andExpect(jsonPath("$.data.envelope.imgUrl").value(
                        giftBoxResponse.envelope().imgUrl()))
                    .andExpect(jsonPath("$.data.envelope.borderColorCode").value(
                        giftBoxResponse.envelope().borderColorCode()))
                    .andExpect(
                        jsonPath("$.data.letterContent").value(giftBoxResponse.letterContent()))
                    .andExpect(jsonPath("$.data.youtubeUrl").value(giftBoxResponse.youtubeUrl()))
                    .andExpect(jsonPath("$.data.photos").isArray())
                    .andExpect(jsonPath("$.data.photos", hasSize(giftBoxResponse.photos().size())))
                    .andExpect(
                        jsonPath("$.data.stickers", hasSize(giftBoxResponse.stickers().size())))
                    .andExpect(jsonPath("$.data.gift.type").value(giftBoxResponse.gift().type()))
                    .andExpect(jsonPath("$.data.gift.url").value(giftBoxResponse.gift().url()));
            }),
            DynamicTest.dynamicTest("선물이 없을 경우", () -> {
                // given
                GiftBoxResponse giftBoxResponse = GiftBoxResponse.builder()
                    .name("test")
                    .senderName("sender")
                    .receiverName("receiver")
                    .box(boxResponse)
                    .envelope(envelopeResponse)
                    .letterContent("This is letter content.")
                    .youtubeUrl("www.youtube.com")
                    .photos(photos)
                    .stickers(stickers)
                    .build();
                given(giftBoxService.openGiftBox(any())).willReturn(giftBoxResponse);

                // when // then
                mockMvc.perform(
                        get(giftBoxBaseUrl + "/{giftBoxId}", anyLong())
                            .with(csrf())
                            .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.name").value(giftBoxResponse.name()))
                    .andExpect(jsonPath("$.data.senderName").value(giftBoxResponse.senderName()))
                    .andExpect(
                        jsonPath("$.data.receiverName").value(giftBoxResponse.receiverName()))
                    .andExpect(
                        jsonPath("$.data.box.id").value(giftBoxResponse.box().id()))
                    .andExpect(
                        jsonPath("$.data.box.boxNormal").value(giftBoxResponse.box().boxNormal()))
                    .andExpect(jsonPath("$.data.envelope.imgUrl").value(
                        giftBoxResponse.envelope().imgUrl()))
                    .andExpect(jsonPath("$.data.envelope.borderColorCode").value(
                        giftBoxResponse.envelope().borderColorCode()))
                    .andExpect(
                        jsonPath("$.data.letterContent").value(giftBoxResponse.letterContent()))
                    .andExpect(jsonPath("$.data.youtubeUrl").value(giftBoxResponse.youtubeUrl()))
                    .andExpect(jsonPath("$.data.photos").isArray())
                    .andExpect(jsonPath("$.data.photos", hasSize(giftBoxResponse.photos().size())))
                    .andExpect(
                        jsonPath("$.data.stickers", hasSize(giftBoxResponse.stickers().size())));
            })
        );
    }
}
