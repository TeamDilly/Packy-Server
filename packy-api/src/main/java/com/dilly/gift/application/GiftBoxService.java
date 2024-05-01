package com.dilly.gift.application;

import com.dilly.admin.adaptor.AdminGiftBoxReader;
import com.dilly.application.FileService;
import com.dilly.exception.ErrorCode;
import com.dilly.exception.GiftBoxAccessDeniedException;
import com.dilly.exception.GiftBoxAlreadyDeletedException;
import com.dilly.exception.GiftBoxAlreadyOpenedException;
import com.dilly.exception.UnsupportedException;
import com.dilly.gift.adaptor.BoxReader;
import com.dilly.gift.adaptor.EnvelopeReader;
import com.dilly.gift.adaptor.GiftBoxReader;
import com.dilly.gift.adaptor.GiftBoxStickerReader;
import com.dilly.gift.adaptor.GiftBoxStickerWriter;
import com.dilly.gift.adaptor.GiftBoxWriter;
import com.dilly.gift.adaptor.LastViewedAdminTypeReader;
import com.dilly.gift.adaptor.LastViewedAdminTypeWriter;
import com.dilly.gift.adaptor.LetterWriter;
import com.dilly.gift.adaptor.PhotoReader;
import com.dilly.gift.adaptor.PhotoWriter;
import com.dilly.gift.adaptor.ReceiverReader;
import com.dilly.gift.adaptor.ReceiverWriter;
import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.gift.Gift;
import com.dilly.gift.domain.gift.GiftType;
import com.dilly.gift.domain.giftbox.DeliverStatus;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.giftbox.GiftBoxRole;
import com.dilly.gift.domain.giftbox.GiftBoxType;
import com.dilly.gift.domain.giftbox.admin.AdminGiftBox;
import com.dilly.gift.domain.giftbox.admin.AdminType;
import com.dilly.gift.domain.giftbox.admin.LastViewedAdminType;
import com.dilly.gift.domain.letter.Envelope;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.gift.domain.receiver.ReceiverStatus;
import com.dilly.gift.dto.request.DeliverStatusRequest;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.response.BoxResponse;
import com.dilly.gift.dto.response.EnvelopeResponse;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftBoxesResponse;
import com.dilly.gift.dto.response.GiftResponseDto.GiftResponse;
import com.dilly.gift.dto.response.KakaoImgResponse;
import com.dilly.gift.dto.response.MainGiftBoxResponse;
import com.dilly.gift.dto.response.PhotoResponseDto.PhotoResponse;
import com.dilly.gift.dto.response.StickerResponse;
import com.dilly.gift.dto.response.WaitingGiftBoxResponse;
import com.dilly.global.util.SecurityUtil;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.domain.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GiftBoxService {

    private final FileService fileService;
    private final GiftBoxReader giftBoxReader;
    private final GiftBoxWriter giftBoxWriter;
    private final BoxReader boxReader;
    private final EnvelopeReader envelopeReader;
    private final LetterWriter letterWriter;
    private final PhotoReader photoReader;
    private final PhotoWriter photoWriter;
    private final GiftBoxStickerReader giftBoxStickerReader;
    private final GiftBoxStickerWriter giftBoxStickerWriter;
    private final MemberReader memberReader;
    private final ReceiverReader receiverReader;
    private final ReceiverWriter receiverWriter;
    private final AdminGiftBoxReader adminGiftBoxReader;
    private final LastViewedAdminTypeReader lastViewedAdminTypeReader;
    private final LastViewedAdminTypeWriter lastViewedAdminTypeWriter;

    public GiftBoxIdResponse createGiftBox(GiftBoxRequest giftBoxRequest) {
        Long memberId = SecurityUtil.getMemberId();
        Member sender = memberReader.findById(memberId);

        Box box = boxReader.findById(giftBoxRequest.boxId());
        Envelope envelope = envelopeReader.findById(giftBoxRequest.envelopeId());
        Letter letter = letterWriter.save(giftBoxRequest.letterContent(), envelope);

        GiftBox giftBox;
        if (giftBoxRequest.gift() == null) {
            giftBox = giftBoxWriter.save(box, letter, sender,
                giftBoxRequest.name(), giftBoxRequest.youtubeUrl(),
                giftBoxRequest.senderName(), giftBoxRequest.receiverName());
        } else {
            Gift gift = Gift.of(GiftType.valueOf(giftBoxRequest.gift().type().toUpperCase()),
                giftBoxRequest.gift().url());

            giftBox = giftBoxWriter.save(box, letter, gift, sender,
                giftBoxRequest.name(), giftBoxRequest.youtubeUrl(),
                giftBoxRequest.senderName(), giftBoxRequest.receiverName()
            );
        }

        giftBoxRequest.photos()
            .forEach(
                photoRequest -> photoWriter.save(giftBox, photoRequest.photoUrl(),
                    photoRequest.description(), photoRequest.sequence())
            );
        giftBoxRequest.stickers()
            .forEach(stickerRequest ->
                giftBoxStickerWriter.save(giftBox, stickerRequest.id(), stickerRequest.location())
            );

        return GiftBoxIdResponse.of(giftBox.getId(), giftBox.getUuid(),
            giftBox.getBox().getKakaoMessageImgUrl());
    }

    // TODO: 가독성 개선하기
    void checkIfGiftBoxOpenable(Member member, GiftBox giftBox) {
        // 카카오톡으로 보내기를 누르지 않은 선물박스
        // 만든 유저는 선물박스에 접근 가능
        if (giftBox.getDeliverStatus().equals(DeliverStatus.WAITING) && (!giftBox.getSender()
            .equals(member))) {
            throw new GiftBoxAccessDeniedException();
        }

        // 선물박스를 만든 유저가 삭제했을 경우, 만든 유저가 선물박스에 접근 불가능
        if (giftBox.getSender().equals(member)) {
            if (giftBox.getSenderDeleted().equals(true)) {
                throw new GiftBoxAlreadyDeletedException();
            }
        } else {
            List<Long> receivers = receiverReader.findByGiftBox(giftBox).stream()
                .map(Receiver::getMember)
                .map(Member::getId)
                .toList();

            // 선물박스를 받은 사람이 없을 경우
            if (receivers.isEmpty()) {
                if (giftBox.getSenderDeleted().equals(false)) {
                    receiverWriter.save(member, giftBox);
                }
            } else { // 선물박스를 받은 사람이 있을 경우
                // 이전에 선물박스를 받은 유저인 경우
                if (receivers.contains(member.getId())) {
                    Receiver receiver = receiverReader.findByMemberAndGiftBox(member, giftBox);
                    // 받은 사람이 삭제했을 경우
                    if (receiver.getStatus().equals(ReceiverStatus.DELETED)) {
                        throw new GiftBoxAlreadyDeletedException();
                    }
                } else { // 이전에 선물박스를 받지 않은 유저인 경우
                    if (giftBox.getGiftBoxType().equals(GiftBoxType.PRIVATE)) {
                        throw new GiftBoxAlreadyOpenedException();
                    }
                }

            }
        }
    }

    public GiftBoxResponse openGiftBox(Long giftBoxId) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);
        GiftBox giftBox = giftBoxReader.findById(giftBoxId);

        checkIfGiftBoxOpenable(member, giftBox);

        return toGiftBoxResponse(giftBox);
    }


    }

    // TODO: 성능 개선 필요
    public Slice<GiftBoxesResponse> getGiftBoxes(LocalDateTime lastGiftBoxDate, String type,
        Pageable pageable) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        if (type == null) {
            type = "all";
        }

        Slice<GiftBox> giftBoxSlice;
        List<GiftBoxesResponse> giftBoxesResponses = new ArrayList<>();
        switch (type) {
            case "sent" -> {
                giftBoxSlice = giftBoxReader.searchSentGiftBoxesBySlice(member,
                    lastGiftBoxDate, pageable);
                giftBoxesResponses = sliceToDto(giftBoxSlice, type, member);
            }
            case "received" -> {
                giftBoxSlice = giftBoxReader.searchReceivedGiftBoxesBySlice(member,
                    lastGiftBoxDate, pageable);
                giftBoxesResponses = sliceToDto(giftBoxSlice, type, member);
            }
            case "all" -> {
                Comparator<Object> comparator = getCreatedAtComparator(member);
                giftBoxSlice = giftBoxReader.searchAllGiftBoxesBySlice(member,
                    lastGiftBoxDate, comparator, pageable);
                giftBoxesResponses = sliceToDto(giftBoxSlice, type, member);
            }
            default -> giftBoxSlice = new SliceImpl<>(List.of(), pageable, false);
        }

        return new SliceImpl<>(giftBoxesResponses, pageable, giftBoxSlice.hasNext());
    }

    private List<GiftBoxesResponse> sliceToDto(Slice<GiftBox> giftBoxSlice, String type,
        Member member) {
        switch (type) {
            case "sent" -> {
                return giftBoxSlice.getContent().stream()
                    .map(GiftBoxesResponse::of)
                    .toList();
            }
            case "received" -> {
                return giftBoxSlice.getContent().stream()
                    .map(giftBox -> {
                        Receiver receiver = receiverReader.findByMemberAndGiftBox(member, giftBox);
                        return GiftBoxesResponse.of(receiver);
                    })
                    .toList();
            }
            case "all" -> {
                return giftBoxSlice.getContent().stream()
                    .map(giftBox -> {
                        Receiver receiver = receiverReader.findByMemberAndGiftBox(member, giftBox);
                        return GiftBoxesResponse.of(giftBox, member, receiver);
                    })
                    .toList();
            }
            default -> throw new UnsupportedException(ErrorCode.UNSUPPORTED_GIFTBOX_TYPE);
        }
    }

    private Comparator<Object> getCreatedAtComparator(Member member) {
        return Comparator.comparing((Object obj) -> {
            GiftBox giftBox = (GiftBox) obj;
            LocalDateTime createdAt;

            if (giftBox.getSender().equals(member)) {
                createdAt = giftBox.getCreatedAt();
            } else {
                Receiver receiver = receiverReader.findByMemberAndGiftBox(member, giftBox);
                createdAt = receiver.getCreatedAt();
            }

            return createdAt;
        });
    }

    public String deleteGiftBox(Long giftBoxId) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        GiftBox giftBox = giftBoxReader.findById(giftBoxId);
        GiftBoxRole role = getGiftBoxRole(member, giftBox);

        if (role.equals(GiftBoxRole.SENDER)) {
            if (giftBox.getDeliverStatus().equals(DeliverStatus.DELIVERED)) {
                giftBox.delete();
            } else if (giftBox.getDeliverStatus().equals(DeliverStatus.WAITING)) {
                letterWriter.delete(giftBox.getLetter());
                giftBox.getPhotos().forEach(photo -> {
                    fileService.deleteFile(photo.getImgUrl());
                    photoWriter.delete(photo);
                });
                if (giftBox.getGift().getGiftType().equals(GiftType.PHOTO)) {
                    fileService.deleteFile(giftBox.getGift().getGiftUrl());
                }
                giftBox.getGiftBoxStickers().forEach(giftBoxStickerWriter::delete);
                giftBoxWriter.delete(giftBox);
            }
        } else if (role.equals(GiftBoxRole.RECEIVER)) {
            Receiver receiver = receiverReader.findByMemberAndGiftBox(member, giftBox);
            receiver.delete();
        }

        return "선물박스가 삭제되었습니다";
    }

    private GiftBoxRole getGiftBoxRole(Member member, GiftBox giftBox) {
        List<Member> receivers = receiverReader.findByGiftBox(giftBox).stream()
            .map(Receiver::getMember)
            .toList();

        if (giftBox.getSender().equals(member)) {
            return GiftBoxRole.SENDER;
        } else if (receivers.contains(member)) { // 받은 사람일 경우
            return GiftBoxRole.RECEIVER;
        } else {
            throw new GiftBoxAccessDeniedException();
        }
    }

    public String updateDeliverStatus(Long giftBoxId, DeliverStatusRequest deliverStatusRequest) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        GiftBox giftBox = giftBoxReader.findById(giftBoxId);

        if (!giftBox.getSender().equals(member)) {
            throw new GiftBoxAccessDeniedException();
        }

        DeliverStatus deliverStatus;
        try {
            deliverStatus = DeliverStatus.valueOf(deliverStatusRequest.deliverStatus());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedException(ErrorCode.UNSUPPORTED_DELIVER_TYPE);
        }

        giftBox.updateDeliverStatus(deliverStatus);

        return "선물박스 전송 상태가 변경되었습니다";
    }

    public List<WaitingGiftBoxResponse> getWaitingGiftBoxes() {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        return giftBoxReader.findTop6BySenderAndDeliverStatusAndSenderDeletedOrderByCreatedAtDesc(
                member, DeliverStatus.WAITING).stream()
            .map(WaitingGiftBoxResponse::from)
            .toList();
    }

    public KakaoImgResponse getKakaoMessageImgUrl(Long giftBoxId) {
        GiftBox giftBox = giftBoxReader.findById(giftBoxId);
        return KakaoImgResponse.from(giftBox.getBox().getKakaoMessageImgUrl());
    }

    public MainGiftBoxResponse getMainGiftBox() {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        Optional<LastViewedAdminType> lastViewedAdminType = lastViewedAdminTypeReader.findByMember(
            member);

        Optional<AdminGiftBox> adminGiftBox = adminGiftBoxReader.findByAdminType(
            AdminType.ONBOARDING);

        MainGiftBoxResponse mainGiftBoxResponse = MainGiftBoxResponse.builder().build();

        if (lastViewedAdminType.isEmpty()) {
            lastViewedAdminTypeWriter.save(LastViewedAdminType.of(member, AdminType.ONBOARDING));

            if (adminGiftBox.isPresent()) {
                mainGiftBoxResponse = MainGiftBoxResponse.from(adminGiftBox.get().getGiftBox());
            }
        }

        return mainGiftBoxResponse;
    }

    public GiftBoxResponse toGiftBoxResponse(GiftBox giftBox) {
        BoxResponse boxResponse = BoxResponse.from(giftBox.getBox());
        EnvelopeResponse envelopeResponse = EnvelopeResponse.from(
            giftBox.getLetter().getEnvelope());
        List<PhotoResponse> photos = photoReader.findAllByGiftBox(giftBox).stream()
            .map(PhotoResponse::from)
            .sorted(Comparator.comparingInt(PhotoResponse::sequence))
            .toList();
        List<StickerResponse> stickers = giftBoxStickerReader.findAllByGiftBox(giftBox).stream()
            .map(StickerResponse::from)
            .sorted(Comparator.comparingInt(StickerResponse::location))
            .toList();

        GiftResponse giftResponse = null;
        if (giftBox.getGift() != null) {
            giftResponse = GiftResponse.from(giftBox.getGift());
        }

        return GiftBoxResponse.of(giftBox, boxResponse, envelopeResponse, photos, stickers,
            giftResponse);
    }
}
