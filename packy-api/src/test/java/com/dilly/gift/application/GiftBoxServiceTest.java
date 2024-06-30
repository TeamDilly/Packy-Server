package com.dilly.gift.application;

import static com.dilly.GiftBoxFixture.createGiftBoxFixture;
import static com.dilly.GiftBoxFixture.sendGiftBoxFixtureWithGift;
import static com.dilly.GiftBoxFixture.sendGiftBoxFixtureWithoutGift;
import static com.dilly.LetterFixture.createLetterFixture;
import static com.dilly.MemberEnumFixture.NORMAL_MEMBER_RECEIVER;
import static com.dilly.MemberEnumFixture.NORMAL_MEMBER_SENDER;
import static com.dilly.MemberEnumFixture.NORMAL_MEMBER_STRANGER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

import com.dilly.exception.GiftBoxAccessDeniedException;
import com.dilly.exception.GiftBoxAlreadyDeletedException;
import com.dilly.exception.GiftBoxAlreadyOpenedException;
import com.dilly.exception.UnsupportedException;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.domain.Photo;
import com.dilly.gift.domain.giftbox.DeliverStatus;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.giftbox.GiftBoxType;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.gift.domain.receiver.ReceiverStatus;
import com.dilly.gift.domain.sticker.GiftBoxSticker;
import com.dilly.gift.dto.request.DeliverStatusRequest;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.GiftRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.request.StickerRequest;
import com.dilly.gift.dto.response.BoxResponse;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftResponseDto.GiftResponse;
import com.dilly.gift.dto.response.KakaoImgResponse;
import com.dilly.gift.dto.response.PhotoResponseDto.PhotoResponse;
import com.dilly.gift.dto.response.StickerResponse;
import com.dilly.global.IntegrationTestSupport;
import com.dilly.global.WithCustomMockUser;
import com.dilly.global.util.validator.UuidValidator;
import com.dilly.member.domain.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class GiftBoxServiceTest extends IntegrationTestSupport {

    private Member MEMBER_SENDER;
    private Member MEMBER_RECEIVER;

    private final String SENDER_ID = "1";
    private final String RECEIVER_ID = "2";
    private final String STRANGER_ID = "3";

    private Letter letter;

    private final List<PhotoRequest> photoRequests = Collections.singletonList(PhotoRequest.of(
        "www.test.com", "description1", 1));

    private final List<StickerRequest> stickerRequests = List.of(
        StickerRequest.of(1L, 1),
        StickerRequest.of(2L, 2)
    );
    private final GiftBoxRequest giftBoxRequestWithGift = GiftBoxRequest.of("test", "sender",
        "receiver",
        1L, 1L, "This is letter content.", "www.youtube.com", photoRequests,
        stickerRequests, GiftRequest.of("photo", "www.test.com"));

    @BeforeEach
    void setUp() {
        Long senderId = Long.parseLong(SENDER_ID);
        Long receiverId = Long.parseLong(RECEIVER_ID);
        Long strangerId = Long.parseLong(STRANGER_ID);

        MEMBER_SENDER = memberWriter.save(NORMAL_MEMBER_SENDER.createMember(senderId));
        MEMBER_RECEIVER = memberWriter.save(NORMAL_MEMBER_RECEIVER.createMember(receiverId));
        memberWriter.save(NORMAL_MEMBER_STRANGER.createMember(strangerId));

        letter = letterWriter.save(createLetterFixture());
    }

    @AfterEach
    void tearDown() {
        memberWriter.deleteAll();
    }

    @Nested
    @DisplayName("선물박스를 만들 때")
    @WithCustomMockUser(id = SENDER_ID)
    class CreateGiftBox {

        @Nested
        @DisplayName("선물박스가 있는 경우")
        class GiftBoxWithGift {

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

            @DisplayName("Gift는 Null로 저장된다.")
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
    
    @Nested
    @DisplayName("선물박스를 열 때")
    class OpenGiftBox {

        @Nested
        @DisplayName("열린 적 없는 선물박스일 경우")
        @WithCustomMockUser(id = RECEIVER_ID)
        class NotOpenedGiftBox {

            @Test
            @DisplayName("선물박스의 정보를 확인할 수 있다.")
            void openGiftBoxWithGift() {
                // given
                GiftBox giftBoxWithGift = sendGiftBoxFixtureWithGift(MEMBER_SENDER, letter);
                giftBoxWriter.save(giftBoxWithGift);

                // TODO: data.sql에 의존하지 않도록 수정해야 함
                BoxResponse expectedBoxResponse = BoxResponse.from(
                    boxReader.findById(1L));
                List<PhotoResponse> expectedPhotoResponses = photoReader.findAllByGiftBox(
                        giftBoxWithGift).stream()
                    .map(PhotoResponse::from)
                    .sorted(Comparator.comparingInt(PhotoResponse::sequence))
                    .toList();
                List<StickerResponse> expectedStickerResponses = giftBoxStickerReader.findAllByGiftBox(
                        giftBoxWithGift).stream()
                    .map(StickerResponse::from)
                    .sorted(Comparator.comparingInt(StickerResponse::location))
                    .toList();
                GiftResponse expectedGiftResponse = GiftResponse.from(giftBoxWithGift.getGift());

                // when
                GiftBoxResponse giftBoxResponse = giftBoxService.openGiftBox(
                    giftBoxWithGift.getId());

                // then
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
            @DisplayName("선물이 없을 경우, Gift는 Null이다.")
            void openGiftBoxWithoutGift() {
                // given
                GiftBox giftBoxWithoutGift = sendGiftBoxFixtureWithoutGift(MEMBER_SENDER, letter);
                giftBoxWriter.save(giftBoxWithoutGift);

                // when
                GiftBoxResponse giftBoxResponse = giftBoxService.openGiftBox(
                    giftBoxWithoutGift.getId());

                // then
                assertThat(giftBoxResponse.gift()).isNull();
            }

            @DisplayName("Receiver 정보가 저장된다.")
            @Test
            void saveReceiverData() {
                // given
                GiftBox giftBoxWithGift = sendGiftBoxFixtureWithGift(MEMBER_SENDER, letter);
                giftBoxWriter.save(giftBoxWithGift);

                Long receiverBefore = receiverReader.countByGiftBox(giftBoxWithGift);

                // when
                giftBoxService.openGiftBox(giftBoxWithGift.getId());
                Long receiverAfter = receiverReader.countByGiftBox(giftBoxWithGift);
                List<Receiver> receivers = receiverReader.findByGiftBox(giftBoxWithGift);

                // then
                assertThat(receiverAfter).isEqualTo(receiverBefore + 1);
                assertThat(receivers).hasSize(1)
                    .extracting("member.id").contains(Long.parseLong(RECEIVER_ID));
            }
        }

        // TODO: 선물박스를 여는 작업을 분리할 방법 찾아보기
        @Nested
        @DisplayName("이미 열린 선물박스일 경우")
        class AlreadyOpenedGiftBox {

            GiftBox giftBox;

            @BeforeEach
            void setUp() {
                giftBox = giftBoxWriter.save(sendGiftBoxFixtureWithGift(MEMBER_SENDER, letter));
                openGiftBox(MEMBER_RECEIVER, giftBox);
            }

            @Nested
            @DisplayName("받은 사람이 선물박스를 다시 여는 경우")
            @WithCustomMockUser(id = RECEIVER_ID)
            class ReceiverReOpenGiftBox {

                @DisplayName("선물박스의 정보를 확인할 수 있다.")
                @Test
                void canopenGiftBox() {
                    // given
                    // TODO: data.sql에 의존하지 않도록 수정해야 함
                    BoxResponse expectedBoxResponse = BoxResponse.from(
                        boxReader.findById(1L));
                    List<PhotoResponse> expectedPhotoResponses = photoReader.findAllByGiftBox(
                            giftBox).stream()
                        .map(PhotoResponse::from)
                        .sorted(Comparator.comparingInt(PhotoResponse::sequence))
                        .toList();
                    List<StickerResponse> expectedStickerResponses = giftBoxStickerReader.findAllByGiftBox(
                            giftBox).stream()
                        .map(StickerResponse::from)
                        .sorted(Comparator.comparingInt(StickerResponse::location))
                        .toList();
                    GiftResponse expectedGiftResponse = GiftResponse.from(giftBox.getGift());

                    // when
                    GiftBoxResponse giftBoxResponse = giftBoxService.openGiftBox(giftBox.getId());

                    // then
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

                @DisplayName("Receiver 정보는 변하지 않는다.")
                @Test
                void receiverDataNotChange() {
                    // given
                    Long receiverBefore = receiverReader.countByGiftBox(giftBox);

                    // when
                    List<Receiver> receivers = receiverReader.findByGiftBox(giftBox);
                    Long receiverAfter = receiverReader.countByGiftBox(giftBox);

                    // then
                    assertThat(receiverAfter).isEqualTo(receiverBefore);
                    assertThat(receivers).hasSize(1)
                        .extracting("member.id").contains(MEMBER_RECEIVER.getId());
                }
            }

            @Test
            @DisplayName("선물박스를 받지 않은 사람은 열 수 없다.")
            @WithCustomMockUser(id = STRANGER_ID)
            void shouldNotAllowRecipientToReopenGiftBox() {
                // given
                Long giftBoxId = giftBox.getId();
                // when // then
                assertThatThrownBy(() -> giftBoxService.openGiftBox(giftBoxId))
                    .isInstanceOf(GiftBoxAlreadyOpenedException.class);
            }
        }
    }

    @Nested
    @DisplayName("선물박스를 웹에서 열 때")
    class OpenGiftBoxForWeb {

        GiftBox giftBox;
        BoxResponse expectedBoxResponse;
        List<PhotoResponse> expectedPhotoResponses;
        List<StickerResponse> expectedStickerResponses;
        GiftResponse expectedGiftResponse;

        @BeforeEach
        void setUp() {
            giftBox = giftBoxWriter.save(sendGiftBoxFixtureWithGift(MEMBER_SENDER, letter));

            // TODO: 중복되는 코드 리팩토링
            expectedBoxResponse = BoxResponse.from(
                boxReader.findById(1L));
            expectedPhotoResponses = photoReader.findAllByGiftBox(
                    giftBox).stream()
                .map(PhotoResponse::from)
                .sorted(Comparator.comparingInt(PhotoResponse::sequence))
                .toList();
            expectedStickerResponses = giftBoxStickerReader.findAllByGiftBox(
                    giftBox).stream()
                .map(StickerResponse::from)
                .sorted(Comparator.comparingInt(StickerResponse::location))
                .toList();
            expectedGiftResponse = GiftResponse.from(giftBox.getGift());
        }

        @DisplayName("UUID로 선물박스를 연다.")
        @Test
        void openGiftBoxByUuid() {
            // when
            GiftBoxResponse giftBoxResponse = giftBoxService.openGiftBoxForWeb(giftBox.getUuid());

            // then
            assertThat(giftBoxResponse.name()).isEqualTo(giftBox.getName());
            assertThat(giftBoxResponse.senderName()).isEqualTo(giftBox.getSenderName());
            assertThat(giftBoxResponse.receiverName()).isEqualTo(
                giftBox.getReceiverName());
            assertThat(giftBoxResponse.box()).isEqualTo(expectedBoxResponse);
            assertThat(giftBoxResponse.letterContent()).isEqualTo(
                giftBox.getLetter().getContent());
            assertThat(giftBoxResponse.youtubeUrl()).isEqualTo(giftBox.getYoutubeUrl());
            assertThat(giftBoxResponse.photos()).isEqualTo(expectedPhotoResponses);
            assertThat(giftBoxResponse.stickers()).isEqualTo(expectedStickerResponses);
            assertThat(giftBoxResponse.gift()).isEqualTo(expectedGiftResponse);
        }

        @DisplayName("선물박스 ID로 선물박스를 연다.")
        @Test
        void openGiftBoxById() {
            // when
            String giftBoxId = Long.toString(giftBox.getId());
            GiftBoxResponse giftBoxResponse = giftBoxService.openGiftBoxForWeb(giftBoxId);

            // then
            assertThat(giftBoxResponse.name()).isEqualTo(giftBox.getName());
            assertThat(giftBoxResponse.senderName()).isEqualTo(giftBox.getSenderName());
            assertThat(giftBoxResponse.receiverName()).isEqualTo(
                giftBox.getReceiverName());
            assertThat(giftBoxResponse.box()).isEqualTo(expectedBoxResponse);
            assertThat(giftBoxResponse.letterContent()).isEqualTo(
                giftBox.getLetter().getContent());
            assertThat(giftBoxResponse.youtubeUrl()).isEqualTo(giftBox.getYoutubeUrl());
            assertThat(giftBoxResponse.photos()).isEqualTo(expectedPhotoResponses);
            assertThat(giftBoxResponse.stickers()).isEqualTo(expectedStickerResponses);
            assertThat(giftBoxResponse.gift()).isEqualTo(expectedGiftResponse);
        }

        @DisplayName("UUID와 ID가 아닌 값으로 선물박스를 열 수 없다.")
        @Test
        void cannotOpenGiftBox() {
            // when // then
            assertThatThrownBy(() -> giftBoxService.openGiftBoxForWeb("test"))
                .isInstanceOf(UnsupportedException.class);
        }

    }

    @Nested
    @DisplayName("선물박스를 삭제하는 사람이")
    class DeleteGiftBox {

        @Nested
        @DisplayName("선물박스를 보낸 사람이라면")
        class IfSender {

            @DisplayName("전송 완료된 선물 박스는")
            @TestFactory
            Collection<DynamicTest> deliveredGiftBox() {
                // given
                GiftBox giftBox = giftBoxWriter.save(
                    sendGiftBoxFixtureWithGift(MEMBER_SENDER, letter));
                Long giftBoxId = giftBox.getId();

                return List.of(
                    DynamicTest.dynamicTest("보낸 사람 측면에서 soft delete된다.", () -> {
                        // given
                        createSecurityContextWithMockUser(SENDER_ID);

                        // when
                        giftBoxService.deleteGiftBox(giftBoxId);

                        // then
                        assertThat(giftBox.getSenderDeleted()).isTrue();
                    }),
                    DynamicTest.dynamicTest("받은 사람은 선물박스에 다시 접근할 수 있다.", () -> {
                        // given
                        createSecurityContextWithMockUser(RECEIVER_ID);
                        // when // then
                        assertThat(giftBoxService.openGiftBox(giftBoxId)).isNotNull();
                    }),
                    DynamicTest.dynamicTest("보낸 사람은 선물박스에 다시 접근할 수 없다.", () -> {
                        // given
                        createSecurityContextWithMockUser(SENDER_ID);

                        // when // then
                        assertThatThrownBy(() -> giftBoxService.openGiftBox(giftBoxId))
                            .isInstanceOf(GiftBoxAlreadyDeletedException.class);
                    })
                );
            }

            @DisplayName("전송 대기 중인 선물박스라면")
            @TestFactory
            Collection<DynamicTest> waitingGiftBox() {
                // given
                GiftBox giftBox = giftBoxWriter.save(createGiftBoxFixture(MEMBER_SENDER, letter));
                Long giftBoxId = giftBox.getId();

                // when
                doNothing().when(fileService).deleteFile(anyString());

                return List.of(
                    DynamicTest.dynamicTest("선물박스 내부 데이터는 hard delete된다.", () -> {
                        // given
                        createSecurityContextWithMockUser(SENDER_ID);

                        int photoCount = giftBox.getPhotos().size();
                        int giftBoxStickerCount = giftBox.getGiftBoxStickers().size();

                        Long letterBefore = letterReader.count();
                        Long photoBefore = photoReader.count();
                        Long giftBoxStickerBefore = giftBoxStickerReader.count();

                        // when
                        giftBoxService.deleteGiftBox(giftBox.getId());
                        Long letterAfter = letterReader.count();
                        Long photoAfter = photoReader.count();
                        Long giftBoxStickerAfter = giftBoxStickerReader.count();

                        // then
                        assertThat(letterAfter).isEqualTo(letterBefore - 1);
                        assertThat(photoAfter).isEqualTo(photoBefore - photoCount);
                        assertThat(giftBoxStickerAfter).isEqualTo(
                            giftBoxStickerBefore - giftBoxStickerCount);
                    }),
                    DynamicTest.dynamicTest("선물박스 삭제 후 엔티티가 삭제된다", () -> {
                        // given
                        createSecurityContextWithMockUser(SENDER_ID);

                        // when // then
                        assertThatThrownBy(() -> giftBoxService.openGiftBox(giftBoxId))
                            .isInstanceOf(EntityNotFoundException.class);
                    })
                );
            }
        }

        // 선물박스를 받았다는 정보를 soft delete한다.
        // 받은 사람은 선물박스에 다시 접근할 수 없다
        // 보낸 사람은 선물박스에  다시 접근할 수 있다
        @DisplayName("선물박스를 받은 사람이라면")
        @TestFactory
        Collection<DynamicTest> ifReceiver() {
            // given
            GiftBox giftBox = giftBoxWriter.save(createGiftBoxFixture(MEMBER_SENDER, letter));
            Receiver receiver = receiverWriter.save(MEMBER_RECEIVER, giftBox);

            Long giftBoxId = giftBox.getId();

            return List.of(
                DynamicTest.dynamicTest("선물박스를 받았다는 정보를 soft delete한다.", () -> {
                    // given
                    createSecurityContextWithMockUser(RECEIVER_ID);

                    // when
                    giftBoxService.deleteGiftBox(giftBoxId);

                    // then
                    assertThat(receiver.getStatus()).isEqualTo(ReceiverStatus.DELETED);
                }),
                DynamicTest.dynamicTest("받은 사람은 선물박스에 다시 접근할 수 없다.", () -> {
                    // given
                    createSecurityContextWithMockUser(RECEIVER_ID);

                    // when // then
                    assertThatThrownBy(() -> giftBoxService.openGiftBox(giftBoxId))
                        .isInstanceOf(GiftBoxAlreadyDeletedException.class);
                }),
                DynamicTest.dynamicTest("보낸 사람은 선물박스에 다시 접근할 수 있다.", () -> {
                    // given
                    createSecurityContextWithMockUser(SENDER_ID);

                    // when // then
                    assertThat(giftBoxService.openGiftBox(giftBoxId)).isNotNull();
                })
            );
        }

        @DisplayName("보낸 사람, 받은 사람 둘 다 아니라면 선물박스를 삭제할 수 없다.")
        @Test
        @WithCustomMockUser(id = STRANGER_ID)
        void strangerCannotDelete() {
            // given
            GiftBox giftBox = giftBoxWriter.save(createGiftBoxFixture(MEMBER_SENDER, letter));
            Long giftBoxId = giftBox.getId();
            // when // then
            assertThatThrownBy(() -> giftBoxService.deleteGiftBox(giftBoxId))
                .isInstanceOf(GiftBoxAccessDeniedException.class);
        }
    }

    @Nested
    @DisplayName("선물박스 전송 상태를")
    class UpdateDeliverStatus {

        private final DeliverStatusRequest deliverStatusRequest = DeliverStatusRequest.builder()
            .deliverStatus("DELIVERED")
            .build();

        @DisplayName("보내는 사람은 변경할 수 있다.")
        @Test
        @WithCustomMockUser(id = SENDER_ID)
        void updateDeliverStatusBySender() {
            // given
            GiftBox giftBox = giftBoxWriter.save(createGiftBoxFixture(MEMBER_SENDER, letter));

            // when
            giftBoxService.updateDeliverStatus(giftBox.getId(), deliverStatusRequest);

            // then
            assertThat(giftBox.getDeliverStatus()).isEqualTo(DeliverStatus.DELIVERED);
        }

        @DisplayName("보내는 사람이 아니면 변경할 수 없다.")
        @Test
        @WithCustomMockUser(id = STRANGER_ID)
        void updateDeliverStatusByStranger() {
            // given
            GiftBox giftBox = giftBoxWriter.save(createGiftBoxFixture(MEMBER_SENDER, letter));
            Long giftBoxId = giftBox.getId();

            // when // then
            assertThatThrownBy(
                () -> giftBoxService.updateDeliverStatus(giftBoxId, deliverStatusRequest))
                .isInstanceOf(GiftBoxAccessDeniedException.class);
        }
    }

    @DisplayName("선물박스 ID로 카카오 메시지 이미지 URL을 조회한다")
    @Test
    @WithCustomMockUser(id = SENDER_ID)
    void getKakaoMessageImgUrl() {
        // given
        GiftBox giftBox = giftBoxWriter.save(sendGiftBoxFixtureWithGift(MEMBER_SENDER, letter));

        // when
        KakaoImgResponse kakaoImgResponse = giftBoxService.getKakaoMessageImgUrl(giftBox.getId());

        // then
        assertThat(kakaoImgResponse.kakaoMessageImgUrl()).isEqualTo(
            giftBox.getBox().getKakaoMessageImgUrl());
    }

    // TODO: GiftBoxService.checkIfGiftBoxOpenable() 리팩토링 후 메서드를 Service 클래스로 옮기기
    private void openGiftBox(Member member, GiftBox giftBox) {
        List<Member> receivers = receiverReader.findByGiftBox(giftBox).stream()
            .map(Receiver::getMember)
            .toList();

        if (!receivers.contains(member)) {
            receiverWriter.save(member, giftBox);
        }
    }
}
