package com.dilly.gift.application;

import static com.dilly.MemberEnumFixture.NORMAL_MEMBER_RECEIVER;
import static com.dilly.MemberEnumFixture.NORMAL_MEMBER_SENDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dilly.exception.GiftBoxAlreadyOpenedException;
import com.dilly.gift.domain.Photo;
import com.dilly.gift.domain.giftbox.DeliverStatus;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.giftbox.GiftBoxType;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.gift.domain.sticker.GiftBoxSticker;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.GiftRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.request.StickerRequest;
import com.dilly.gift.dto.response.BoxResponse;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftResponseDto.GiftResponse;
import com.dilly.gift.dto.response.PhotoResponseDto.PhotoResponse;
import com.dilly.gift.dto.response.StickerResponse;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.global.util.validator.UuidValidator;
import com.dilly.member.domain.Member;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GiftBoxServiceTest extends IntegrationTestSupport {

    private Member MEMBER_SENDER;
    private Member MEMBER_RECEIVER;
    private Member MEMBER_STRANGER;

    private final String SENDER_ID = "1";
    private final String RECEIVER_ID = "2";
    private final String STRANGER_ID = "3";

    @BeforeEach
    void setUp() {
        Long senderId = Long.parseLong(SENDER_ID);
        Long receiverId = Long.parseLong(RECEIVER_ID);
        Long strangerId = Long.parseLong(STRANGER_ID);

        MEMBER_SENDER = memberWriter.save(NORMAL_MEMBER_SENDER.createMember(senderId));
        MEMBER_RECEIVER = memberWriter.save(NORMAL_MEMBER_RECEIVER.createMember(receiverId));
        MEMBER_STRANGER = memberWriter.save(NORMAL_MEMBER_SENDER.createMember(strangerId));
    }

    @AfterEach
    void tearDown() {
        memberWriter.deleteAll();
    }

    @Nested
    @DisplayName("선물박스를 만들 때")
    @WithCustomMockUser(id = SENDER_ID)
    class CreateGiftBox {

        // given
        List<PhotoRequest> photoRequests = Collections.singletonList(PhotoRequest.of(
            "www.test.com", "description1", 1));

        List<StickerRequest> stickerRequests = List.of(
            StickerRequest.of(1L, 1),
            StickerRequest.of(2L, 2)
        );

        @Nested
        @DisplayName("선물박스가 있는 경우")
        class GiftBoxWithGift {

            // given
            GiftBoxRequest giftBoxRequestWithGift = GiftBoxRequest.of("test", "sender", "receiver",
                1L, 1L, "This is letter content.", "www.youtube.com", photoRequests,
                stickerRequests, GiftRequest.of("photo", "www.test.com"));

            @DisplayName("요청한 정보가 정상적으로 저장된다")
            @Test
            void saveGiftBoxInfo() {
                // when
                giftBoxService.createGiftBox(giftBoxRequestWithGift);
                GiftBox giftBox = giftBoxWriter.findTopByOrderByIdDesc();
                List<Photo> photos = photoReader.findAllByGiftBox(giftBox);
                List<GiftBoxSticker> giftBoxStickers = giftBoxStickerReader
                    .findAllByGiftBox(giftBox);

                // then
                assertThat(giftBox.getSender()).isEqualTo(MEMBER_SENDER);
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
            }

            @DisplayName("저장된 선물박스의 정보를 반환한다")
            @Test
            void returnGiftBoxInfo() {
                // when
                GiftBoxIdResponse giftBoxIdResponse = giftBoxService.createGiftBox(
                    giftBoxRequestWithGift);
                GiftBox giftBox = giftBoxReader.findTopByOrderByIdDesc();

                // then
                assertThat(giftBoxIdResponse.id()).isEqualTo(giftBox.getId());
                assertTrue(UuidValidator.isValidUUID(giftBoxIdResponse.uuid()));
                assertThat(giftBoxIdResponse.kakaoMessageImgUrl()).isEqualTo(
                    giftBox.getBox().getKakaoMessageImgUrl());
            }
        }

        @Nested
        @DisplayName("선물박스가 없는 경우")
        class GiftBoxWithoutGift {

            @DisplayName("Gift는 Null이다.")
            @Test
            void giftIsNull() {
                // given
                GiftBoxRequest giftBoxRequestWithoutGift = GiftBoxRequest.of("test", "sender",
                    "receiver", 1L, 1L, "This is letter content.", "www.youtube.com", photoRequests,
                    stickerRequests);

                // when
                giftBoxService.createGiftBox(giftBoxRequestWithoutGift);
                GiftBox giftBox = giftBoxReader.findTopByOrderByIdDesc();

                // then
                assertThat(giftBox.getGift()).isNull();
            }
        }
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
        BoxResponse expectedBoxResponse = BoxResponse.from(
            boxRepository.findById(1L).orElseThrow());

        @Nested
        @DisplayName("열린 적 없는 선물박스일 경우")
        class ContextWithGift {

            @Test
            @DisplayName("선물이 있을 경우")
            @WithCustomMockUser(id = RECEIVER_ID)
            void openGiftBoxWithGift() {
                // given
                giftBoxWithGift = createMockGiftBoxWithGift(member1, DeliverStatus.DELIVERED);
                Long receiverBefore = receiverRepository.countByGiftBox(giftBoxWithGift);
                List<PhotoResponse> expectedPhotoResponses = photoRepository.findAllByGiftBox(
                        giftBoxWithGift).stream()
                    .map(PhotoResponse::from)
                    .sorted(Comparator.comparingInt(PhotoResponse::sequence))
                    .toList();
                List<StickerResponse> expectedStickerResponses = giftBoxStickerRepository.findAllByGiftBox(
                        giftBoxWithGift).stream()
                    .map(StickerResponse::from)
                    .sorted(Comparator.comparingInt(StickerResponse::location))
                    .toList();
                GiftResponse expectedGiftResponse = GiftResponse.from(giftBoxWithGift.getGift());

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
            @WithCustomMockUser(id = RECEIVER_ID)
            void openGiftBoxWithoutGift() {
                // given
                giftBoxWithoutGift = createMockGiftBoxWithoutGift(member1, DeliverStatus.DELIVERED);
                Long receiverBefore = receiverRepository.countByGiftBox(giftBoxWithoutGift);
                List<PhotoResponse> expectedPhotoResponses = photoRepository.findAllByGiftBox(
                        giftBoxWithoutGift).stream()
                    .map(PhotoResponse::from)
                    .sorted(Comparator.comparingInt(PhotoResponse::sequence))
                    .toList();
                List<StickerResponse> expectedStickerResponses = giftBoxStickerRepository.findAllByGiftBox(
                        giftBoxWithoutGift).stream()
                    .map(StickerResponse::from)
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

            GiftBox giftBox = createMockGiftBoxWithGift(member1, DeliverStatus.DELIVERED);

            @Test
            @DisplayName("선물박스를 이전에 받은 사람은 다시 열 수 있다.")
            @WithCustomMockUser(id = RECEIVER_ID)
            void shouldAllowRecipientToReopenGiftBox() {
                // given
                openGiftBox(member2, giftBox);
                Long receiverBefore = receiverRepository.countByGiftBox(giftBox);
                List<PhotoResponse> expectedPhotoResponses = photoRepository.findAllByGiftBox(
                        giftBox).stream()
                    .map(PhotoResponse::from)
                    .sorted(Comparator.comparingInt(PhotoResponse::sequence))
                    .toList();
                List<StickerResponse> expectedStickerResponses = giftBoxStickerRepository.findAllByGiftBox(
                        giftBox).stream()
                    .map(StickerResponse::from)
                    .sorted(Comparator.comparingInt(StickerResponse::location))
                    .toList();
                GiftResponse expectedGiftResponse = GiftResponse.from(giftBox.getGift());

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
            @WithCustomMockUser(id = STRANGER_ID)
            void shouldNotAllowRecipientToReopenGiftBox() {
                // given
                openGiftBox(member2, giftBox);

                // when // then
                assertThatThrownBy(() -> giftBoxService.openGiftBox(giftBox.getId()))
                    .isInstanceOf(GiftBoxAlreadyOpenedException.class);
            }
        }

// TODO: TSID 적용에 따라 수정 필요

//        @DisplayName("보내지 않은 선물박스를 최신순으로 6개 조회한다.")
//        @Test
//        @WithCustomMockUser
//        void getWaitingGiftBoxes() {
//            // given
//            Member member = memberRepository.findById(1L).orElseThrow();
//            for (int i = 0; i < 10; i++) {
//                createMockGiftBoxWithGift(member, DeliverStatus.WAITING);
//            }
//            Long lastGiftBoxId = giftBoxRepository.findTopByOrderByIdDesc().getId();
//
//            // when
//            List<WaitingGiftBoxResponse> result = giftBoxService.getWaitingGiftBoxes();
//
//            // then
//            assertThat(result).hasSize(6);
//            assertThat(result.get(0).id()).isEqualTo(lastGiftBoxId);
//            assertThat(result.get(5).id()).isEqualTo(lastGiftBoxId - 5);
//        }
    }
}
