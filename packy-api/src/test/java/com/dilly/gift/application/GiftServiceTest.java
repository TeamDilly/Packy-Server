package com.dilly.gift.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dilly.gift.GiftBox;
import com.dilly.gift.GiftBoxSticker;
import com.dilly.gift.GiftBoxType;
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
import java.util.Collections;
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

        List<PhotoRequest> photoRequests = Collections.singletonList(PhotoRequest.of(
            "www.test.com", "description1", 1));

        List<StickerRequest> stickerRequests = List.of(
            StickerRequest.of(1L, 1),
            StickerRequest.of(2L, 2)
        );

        return List.of(
            DynamicTest.dynamicTest("선물이 있을 경우", () -> {
                //given
                GiftBoxRequest giftBoxRequest = GiftBoxRequest.of("test", "sender", "receiver", 1L,
                    1L, "This is letter content.", "www.youtube.com", photoRequests,
                    stickerRequests,
                    GiftRequest.of("photo", "www.test.com"));

                // when
                GiftBoxIdResponse giftBoxIdResponse = giftService.createGiftBox(giftBoxRequest);
                GiftBox giftBox = giftBoxRepository.findTopByOrderByIdDesc();
                List<Photo> photos = photoRepository.findAllByGiftBox(giftBox);
                List<GiftBoxSticker> stickers = giftBoxStickerRepository.findAllByGiftBox(giftBox);

                // then
                assertThat(giftBox.getSender()).isEqualTo(member);
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
                assertThat(photos).hasSize(photoRequests.size())
                    .extracting("imgUrl", "description", "sequence")
                    .contains(tuple("www.test.com", "description1", 1));
                assertThat(stickers).hasSize(stickers.size());
                assertThat(giftBox.getGiftBoxType()).isEqualTo(GiftBoxType.PRIVATE);

                assertThat(giftBoxIdResponse.id()).isEqualTo(giftBox.getId());
                assertTrue(isValidUUID(giftBoxIdResponse.uuid()));
            }),
            DynamicTest.dynamicTest("선물이 없을 경우", () -> {
                //given
                GiftBoxRequest giftBoxRequest = GiftBoxRequest.of("test", "sender", "receiver", 1L,
                    1L, "This is letter content.", "www.youtube.com", photoRequests,
                    stickerRequests);

                // when
                GiftBoxIdResponse giftBoxIdResponse = giftService.createGiftBox(giftBoxRequest);
                GiftBox giftBox = giftBoxRepository.findTopByOrderByIdDesc();
                List<Photo> photos = photoRepository.findAllByGiftBox(giftBox);
                List<GiftBoxSticker> stickers = giftBoxStickerRepository.findAllByGiftBox(giftBox);

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
                assertThat(photos).hasSize(photoRequests.size())
                    .extracting("imgUrl", "description", "sequence")
                    .contains(tuple("www.test.com", "description1", 1));
                assertThat(stickers).hasSize(stickers.size());
                assertThat(giftBox.getGiftBoxType()).isEqualTo(GiftBoxType.PRIVATE);

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
