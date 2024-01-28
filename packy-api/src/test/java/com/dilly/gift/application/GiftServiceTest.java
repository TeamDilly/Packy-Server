package com.dilly.gift.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dilly.gift.GiftBox;
import com.dilly.gift.Photo;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.GiftRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.request.StickerRequest;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.member.Member;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class GiftServiceTest extends IntegrationTestSupport {

    @DisplayName("선물박스 만들기 시나리오")
    @TestFactory
    @WithCustomMockUser
    Collection<DynamicTest> createGiftBox() {
        // given
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberRepository.findById(memberId).orElseThrow();

        List<PhotoRequest> photoRequests = List.of(
            PhotoRequest.builder()
                .photoUrl("www.test1.com").description("description1").sequence(1)
                .build(),
            PhotoRequest.builder()
                .photoUrl("www.test2.com").description("description2").sequence(2)
                .build()
        );
        List<StickerRequest> stickers = List.of(
            StickerRequest.builder().id(1L).location(1).build(),
            StickerRequest.builder().id(2L).location(2).build());

        return List.of(
            DynamicTest.dynamicTest("선물이 있을 경우", () -> {
                //given
                GiftBoxRequest giftBoxRequest = GiftBoxRequest.builder()
                    .name("test")
                    .senderName("sender")
                    .receiverName("receiver")
                    .boxId(1L)
                    .envelopeId(1L)
                    .letterContent("This is letter content.")
                    .youtubeUrl("www.youtube.com")
                    .photos(photoRequests)
                    .stickers(stickers)
                    .gift(GiftRequest.builder()
                        .type("photo")
                        .url("www.naver.com")
                        .build()
                    )
                    .build();

                // when
                GiftBoxIdResponse giftBoxIdResponse = giftService.createGiftBox(giftBoxRequest);
                GiftBox giftBox = giftBoxRepository.findTopByOrderByIdDesc();
                List<Photo> photos = photoRepository.findAllByGiftBox(giftBox);

                // then
                assertThat(giftBox.getBox().getId()).isEqualTo(giftBoxRequest.boxId());
                assertThat(giftBox.getName()).isEqualTo(giftBoxRequest.name());
                assertThat(giftBox.getSenderName()).isEqualTo(giftBoxRequest.senderName());
                assertThat(giftBox.getReceiverName()).isEqualTo(giftBoxRequest.receiverName());
                assertThat(giftBox.getLetter().getEnvelope().getId()).isEqualTo(
                    giftBoxRequest.envelopeId());
                assertThat(giftBox.getLetter().getContent()).isEqualTo(
                    giftBoxRequest.letterContent());
                assertThat(giftBox.getYoutubeUrl()).isEqualTo(giftBoxRequest.youtubeUrl());
                assertThat(giftBox.getGift().getGiftType().name()).isEqualTo(
                    giftBoxRequest.gift().type().toUpperCase());
                assertThat(giftBox.getGift().getGiftUrl()).isEqualTo(giftBoxRequest.gift().url());
                assertThat(photos).hasSize(2)
                    .extracting("imgUrl", "description", "sequence")
                    .contains(tuple("www.test1.com", "description1", 1),
                        tuple("www.test2.com", "description2", 2));

                assertThat(giftBoxIdResponse.id()).isEqualTo(giftBox.getId());
                assertTrue(isValidUUID(giftBoxIdResponse.uuid()));
            }),
            DynamicTest.dynamicTest("선물이 없을 경우", () -> {
                //given
                GiftBoxRequest giftBoxRequest = GiftBoxRequest.builder()
                    .name("test")
                    .senderName("sender")
                    .receiverName("receiver")
                    .boxId(1L)
                    .envelopeId(1L)
                    .letterContent("This is letter content.")
                    .youtubeUrl("www.youtube.com")
                    .photos(photoRequests)
                    .stickers(stickers)
                    .build();

                // when
                GiftBoxIdResponse giftBoxIdResponse = giftService.createGiftBox(giftBoxRequest);
                GiftBox giftBox = giftBoxRepository.findTopByOrderByIdDesc();
                List<Photo> photos = photoRepository.findAllByGiftBox(giftBox);

                // then
                assertThat(giftBox.getBox().getId()).isEqualTo(giftBoxRequest.boxId());
                assertThat(giftBox.getName()).isEqualTo(giftBoxRequest.name());
                assertThat(giftBox.getSenderName()).isEqualTo(giftBoxRequest.senderName());
                assertThat(giftBox.getReceiverName()).isEqualTo(giftBoxRequest.receiverName());
                assertThat(giftBox.getLetter().getEnvelope().getId()).isEqualTo(
                    giftBoxRequest.envelopeId());
                assertThat(giftBox.getLetter().getContent()).isEqualTo(
                    giftBoxRequest.letterContent());
                assertThat(giftBox.getYoutubeUrl()).isEqualTo(giftBoxRequest.youtubeUrl());
                assertThat(giftBox.getGift()).isNull();
                assertThat(photos).hasSize(2)
                    .extracting("imgUrl", "description", "sequence")
                    .contains(tuple("www.test1.com", "description1", 1),
                        tuple("www.test2.com", "description2", 2));

                assertThat(giftBoxIdResponse.id()).isEqualTo(giftBox.getId());
                assertTrue(isValidUUID(giftBoxIdResponse.uuid()));
            })
        );
    }


    private boolean isValidUUID(String value) {
        final Pattern uuidPattern =
            Pattern.compile(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        return value != null && uuidPattern.matcher(value).matches();
    }
}
