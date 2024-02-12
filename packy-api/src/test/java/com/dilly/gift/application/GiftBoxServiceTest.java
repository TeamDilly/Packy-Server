package com.dilly.gift.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dilly.exception.GiftBoxAlreadyOpenedException;
import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.GiftBoxSticker;
import com.dilly.gift.domain.GiftBoxType;
import com.dilly.gift.domain.Photo;
import com.dilly.gift.domain.Receiver;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.GiftRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.request.StickerRequest;
import com.dilly.gift.dto.response.BoxResponse;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftResponse;
import com.dilly.gift.dto.response.PhotoResponseDto.PhotoResponse;
import com.dilly.gift.dto.response.StickerResponse;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.member.domain.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class GiftBoxServiceTest extends IntegrationTestSupport {

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
        GiftBoxRequest giftBoxRequestWithGift = GiftBoxRequest.of("test", "sender", "receiver", 1L,
            1L, "This is letter content.", "www.youtube.com", photoRequests,
            stickerRequests,
            GiftRequest.of("photo", "www.test.com"));
        GiftBoxRequest giftBoxRequestWithoutGift = GiftBoxRequest.of("test", "sender", "receiver",
            1L,
            1L, "This is letter content.", "www.youtube.com", photoRequests,
            stickerRequests);

        return List.of(
            DynamicTest.dynamicTest("선물이 있을 경우", () -> {
                // when
                GiftBoxIdResponse giftBoxIdResponse = giftBoxService.createGiftBox(
                    giftBoxRequestWithGift);
                GiftBox giftBox = giftBoxRepository.findTopByOrderByIdDesc();
                List<Photo> photos = photoRepository.findAllByGiftBox(giftBox);
                List<GiftBoxSticker> giftBoxStickers = giftBoxStickerRepository
                    .findAllByGiftBox(giftBox);

                // then
                assertThat(giftBox.getSender()).isEqualTo(member);
                assertThat(giftBox.getBox().getId()).isEqualTo(giftBoxRequestWithGift.boxId());
                assertThat(giftBox.getName()).isEqualTo(giftBoxRequestWithGift.name());
                assertThat(giftBox.getSenderName()).isEqualTo(giftBoxRequestWithGift.senderName());
                assertThat(giftBox.getReceiverName()).isEqualTo(
                    giftBoxRequestWithGift.receiverName());
                assertThat(giftBox.getLetter().getEnvelope().getId()).isEqualTo(
                    giftBoxRequestWithGift.envelopeId());
                assertThat(giftBox.getLetter().getContent()).isEqualTo(
                    giftBoxRequestWithGift.letterContent());
                assertThat(giftBox.getYoutubeUrl()).isEqualTo(giftBoxRequestWithGift.youtubeUrl());
                assertThat(giftBox.getGift().getGiftType().name()).isEqualTo(
                    giftBoxRequestWithGift.gift().type().toUpperCase());
                assertThat(giftBox.getGift().getGiftUrl()).isEqualTo(
                    giftBoxRequestWithGift.gift().url());
                assertThat(photos).hasSize(photoRequests.size())
                    .extracting("imgUrl", "description", "sequence")
                    .contains(tuple("www.test.com", "description1", 1));
                assertThat(giftBoxStickers).hasSize(stickerRequests.size());
                assertThat(giftBox.getGiftBoxType()).isEqualTo(GiftBoxType.PRIVATE);

                assertThat(giftBoxIdResponse.id()).isEqualTo(giftBox.getId());
                assertTrue(isValidUUID(giftBoxIdResponse.uuid()));
            }),
            DynamicTest.dynamicTest("선물이 없을 경우", () -> {
                // when
                GiftBoxIdResponse giftBoxIdResponse = giftBoxService.createGiftBox(
                    giftBoxRequestWithoutGift);
                GiftBox giftBox = giftBoxRepository.findTopByOrderByIdDesc();
                List<Photo> photos = photoRepository.findAllByGiftBox(giftBox);
                List<GiftBoxSticker> giftBoxStickers = giftBoxStickerRepository
                    .findAllByGiftBox(giftBox);

                // then
                assertThat(giftBox.getSender()).isEqualTo(member);
                assertThat(giftBox.getBox().getId()).isEqualTo(giftBoxRequestWithoutGift.boxId());
                assertThat(giftBox.getName()).isEqualTo(giftBoxRequestWithoutGift.name());
                assertThat(giftBox.getSenderName()).isEqualTo(
                    giftBoxRequestWithoutGift.senderName());
                assertThat(giftBox.getReceiverName()).isEqualTo(
                    giftBoxRequestWithoutGift.receiverName());
                assertThat(giftBox.getLetter().getEnvelope().getId()).isEqualTo(
                    giftBoxRequestWithoutGift.envelopeId());
                assertThat(giftBox.getLetter().getContent()).isEqualTo(
                    giftBoxRequestWithoutGift.letterContent());
                assertThat(giftBox.getYoutubeUrl()).isEqualTo(
                    giftBoxRequestWithoutGift.youtubeUrl());
                assertThat(giftBox.getGift()).isNull();
                assertThat(photos).hasSize(photoRequests.size())
                    .extracting("imgUrl", "description", "sequence")
                    .contains(tuple("www.test.com", "description1", 1));
                assertThat(giftBoxStickers).hasSize(stickerRequests.size());
                assertThat(giftBox.getGiftBoxType()).isEqualTo(GiftBoxType.PRIVATE);

                assertThat(giftBoxIdResponse.id()).isEqualTo(giftBox.getId());
                assertTrue(isValidUUID(giftBoxIdResponse.uuid()));
            })
        );
    }

    // 1번 유저가 선물 박스를 만든다
    // 2번 유저가 선물 박스를 연다
    // receiver에 2번 유저가 들어갔는지 check
    // 선물박스 정보가 다 담겼는지 check

    // 2번 유저가 선물 박스를 열고, 3번 유저가 또 열었을 때 열리지 않아야 한다.
    @Nested
    @DisplayName("선물박스를 열 때")
    class OpenGiftBox {

        // given
        Member member1 = memberRepository.findById(1L).orElseThrow();
        Member member2 = memberRepository.findById(2L).orElseThrow();

        GiftBox giftBoxWithGift;
        GiftBox giftBoxWithoutGift;
        BoxResponse expectedBoxResponse = BoxResponse.of(
            boxRepository.findById(1L).orElseThrow());

        @Nested
        @DisplayName("열린 적 없는 선물박스일 경우")
        class ContextWithGift {

            @Test
            @DisplayName("선물이 있을 경우")
            @WithCustomMockUser(id = "2")
            void openGiftBoxWithGift() {
                // given
                giftBoxWithGift = createMockGiftBoxWithGift(member1);
                Long receiverBefore = receiverRepository.countByGiftBox(giftBoxWithGift);
                List<PhotoResponse> expectedPhotoResponses = photoRepository.findAllByGiftBox(
                        giftBoxWithGift).stream()
                    .map(PhotoResponse::of)
                    .sorted(Comparator.comparingInt(PhotoResponse::sequence))
                    .toList();
                List<StickerResponse> expectedStickerResponses = giftBoxStickerRepository.findAllByGiftBox(
                        giftBoxWithGift).stream()
                    .map(StickerResponse::of)
                    .sorted(Comparator.comparingInt(StickerResponse::location))
                    .toList();
                GiftResponse expectedGiftResponse = GiftResponse.of(giftBoxWithGift.getGift());

                // when
                GiftBoxResponse giftBoxResponse = giftBoxService.openGiftBox(
                    giftBoxWithGift.getId());
                Long receiverAfter = receiverRepository.countByGiftBox(giftBoxWithGift);
                List<Receiver> receivers = receiverRepository.findByGiftBox(giftBoxWithGift);

                // then
                assertThat(receiverAfter).isEqualTo(receiverBefore + 1);
                assertThat(receivers).hasSize(1)
                    .extracting("member.id").contains(member2.getId());

                assertThat(giftBoxResponse.name()).isEqualTo(giftBoxWithGift.getName());
                assertThat(giftBoxResponse.senderName()).isEqualTo(giftBoxWithGift.getSenderName());
                assertThat(giftBoxResponse.receiverName()).isEqualTo(
                    giftBoxWithGift.getReceiverName());
                assertThat(giftBoxResponse.box()).isEqualTo(expectedBoxResponse);
                assertThat(giftBoxResponse.letterContent()).isEqualTo(
                    giftBoxWithGift.getLetter().getContent());
                assertThat(giftBoxResponse.youtubeUrl()).isEqualTo(giftBoxWithGift.getYoutubeUrl());
                assertThat(giftBoxResponse.photos()).isEqualTo(expectedPhotoResponses);
                assertThat(giftBoxResponse.stickers()).isEqualTo(expectedStickerResponses);
                assertThat(giftBoxResponse.gift()).isEqualTo(expectedGiftResponse);
            }

            @Test
            @DisplayName("선물이 없을 경우")
            @WithCustomMockUser(id = "2")
            void openGiftBoxWithoutGift() {
                // given
                giftBoxWithoutGift = createMockGiftBoxWithoutGift(member1);
                Long receiverBefore = receiverRepository.countByGiftBox(giftBoxWithoutGift);
                List<PhotoResponse> expectedPhotoResponses = photoRepository.findAllByGiftBox(
                        giftBoxWithoutGift).stream()
                    .map(PhotoResponse::of)
                    .sorted(Comparator.comparingInt(PhotoResponse::sequence))
                    .toList();
                List<StickerResponse> expectedStickerResponses = giftBoxStickerRepository.findAllByGiftBox(
                        giftBoxWithoutGift).stream()
                    .map(StickerResponse::of)
                    .sorted(Comparator.comparingInt(StickerResponse::location))
                    .toList();

                // when
                GiftBoxResponse giftBoxResponse = giftBoxService.openGiftBox(
                    giftBoxWithoutGift.getId());
                Long receiverAfter = receiverRepository.countByGiftBox(giftBoxWithoutGift);
                List<Receiver> receivers = receiverRepository.findByGiftBox(giftBoxWithoutGift);

                // then
                assertThat(receiverAfter).isEqualTo(receiverBefore + 1);
                assertThat(receivers).hasSize(1)
                    .extracting("member.id").contains(member2.getId());
                assertThat(giftBoxResponse.name()).isEqualTo(giftBoxWithoutGift.getName());
                assertThat(giftBoxResponse.senderName()).isEqualTo(
                    giftBoxWithoutGift.getSenderName());
                assertThat(giftBoxResponse.receiverName()).isEqualTo(
                    giftBoxWithoutGift.getReceiverName());
                assertThat(giftBoxResponse.box()).isEqualTo(expectedBoxResponse);
                assertThat(giftBoxResponse.letterContent()).isEqualTo(
                    giftBoxWithoutGift.getLetter().getContent());
                assertThat(giftBoxResponse.youtubeUrl()).isEqualTo(
                    giftBoxWithoutGift.getYoutubeUrl());
                assertThat(giftBoxResponse.photos()).isEqualTo(expectedPhotoResponses);
                assertThat(giftBoxResponse.stickers()).isEqualTo(expectedStickerResponses);
                assertThat(giftBoxResponse.gift()).isNull();
            }
        }

        @Nested
        @DisplayName("이미 열린 선물박스일 경우")
        class ContextWithAlreadyOpenedGiftBox {

            GiftBox giftBox = createMockGiftBoxWithGift(member1);

            @Test
            @DisplayName("선물박스를 이전에 받은 사람은 다시 열 수 있다.")
            @WithCustomMockUser(id = "2")
            void shouldAllowRecipientToReopenGiftBox() {
                // given
                openGiftBox(member2, giftBox);
                Long receiverBefore = receiverRepository.countByGiftBox(giftBox);
                List<PhotoResponse> expectedPhotoResponses = photoRepository.findAllByGiftBox(
                        giftBox).stream()
                    .map(PhotoResponse::of)
                    .sorted(Comparator.comparingInt(PhotoResponse::sequence))
                    .toList();
                List<StickerResponse> expectedStickerResponses = giftBoxStickerRepository.findAllByGiftBox(
                        giftBox).stream()
                    .map(StickerResponse::of)
                    .sorted(Comparator.comparingInt(StickerResponse::location))
                    .toList();
                GiftResponse expectedGiftResponse = GiftResponse.of(giftBox.getGift());

                // when
                GiftBoxResponse giftBoxResponse = giftBoxService.openGiftBox(giftBox.getId());
                List<Receiver> receivers = receiverRepository.findByGiftBox(giftBox);
                Long receiverAfter = receiverRepository.countByGiftBox(giftBox);

                // then
                assertThat(receiverAfter).isEqualTo(receiverBefore);
                assertThat(receivers).hasSize(1)
                    .extracting("member.id").contains(member2.getId());
                assertThat(giftBoxResponse.name()).isEqualTo(giftBox.getName());
                assertThat(giftBoxResponse.senderName()).isEqualTo(
                    giftBox.getSenderName());
                assertThat(giftBoxResponse.receiverName()).isEqualTo(
                    giftBox.getReceiverName());
                assertThat(giftBoxResponse.box()).isEqualTo(expectedBoxResponse);
                assertThat(giftBoxResponse.letterContent()).isEqualTo(
                    giftBox.getLetter().getContent());
                assertThat(giftBoxResponse.youtubeUrl()).isEqualTo(
                    giftBox.getYoutubeUrl());
                assertThat(giftBoxResponse.photos()).isEqualTo(expectedPhotoResponses);
                assertThat(giftBoxResponse.stickers()).isEqualTo(expectedStickerResponses);
                assertThat(giftBoxResponse.gift()).isEqualTo(expectedGiftResponse);
            }

            @Test
            @DisplayName("선물박스를 받지 않은 사람은 열 수 없다.")
            @WithCustomMockUser(id = "3")
            void shouldNotAllowRecipientToReopenGiftBox() {
                // given
                openGiftBox(member2, giftBox);

                // when // then
                assertThatThrownBy(() -> giftBoxService.openGiftBox(giftBox.getId()))
                    .isInstanceOf(GiftBoxAlreadyOpenedException.class);
            }
        }
    }

    private boolean isValidUUID(String value) {
        final Pattern uuidPattern =
            Pattern.compile(
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        return value != null && uuidPattern.matcher(value).matches();
    }
}
