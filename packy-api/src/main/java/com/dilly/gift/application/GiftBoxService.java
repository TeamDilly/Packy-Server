package com.dilly.gift.application;

import static com.dilly.gift.domain.GiftBoxType.PRIVATE;

import com.dilly.exception.GiftBoxAlreadyOpenedException;
import com.dilly.gift.adaptor.BoxReader;
import com.dilly.gift.adaptor.EnvelopeReader;
import com.dilly.gift.adaptor.GiftBoxReader;
import com.dilly.gift.adaptor.GiftBoxStickerReader;
import com.dilly.gift.adaptor.GiftBoxStickerWriter;
import com.dilly.gift.adaptor.GiftBoxWriter;
import com.dilly.gift.adaptor.LetterWriter;
import com.dilly.gift.adaptor.PhotoReader;
import com.dilly.gift.adaptor.PhotoWriter;
import com.dilly.gift.adaptor.ReceiverReader;
import com.dilly.gift.adaptor.ReceiverWriter;
import com.dilly.gift.domain.Box;
import com.dilly.gift.domain.Envelope;
import com.dilly.gift.domain.Gift;
import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.GiftType;
import com.dilly.gift.domain.Letter;
import com.dilly.gift.domain.Receiver;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.response.BoxResponse;
import com.dilly.gift.dto.response.EnvelopeResponse;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftBoxesResponse;
import com.dilly.gift.dto.response.GiftResponse;
import com.dilly.gift.dto.response.PhotoResponse;
import com.dilly.gift.dto.response.StickerResponse;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.domain.Member;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
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

        return GiftBoxIdResponse.of(giftBox.getId(), giftBox.getUuid());
    }

    public GiftBoxResponse openGiftBox(Long giftBoxId) {
        Long memberId = SecurityUtil.getMemberId();
        Member receiver = memberReader.findById(memberId);
        GiftBox giftBox = giftBoxReader.findById(giftBoxId);

        List<Long> receivers = receiverReader.findByGiftBox(giftBox).stream()
            .map(Receiver::getMember)
            .map(Member::getId)
            .toList();

        // 1명만 열 수 있는 선물박스이고, 이미 열린 선물박스일 경우
        if (giftBox.getGiftBoxType().equals(PRIVATE)
            && !receivers.isEmpty()
            && !receivers.get(0).equals(receiver.getId())) {
            throw new GiftBoxAlreadyOpenedException();
        }

        // 선물박스를 이전에 열지 않았던 경우
        if (!receivers.contains(receiver.getId())) {
            receiverWriter.save(receiver, giftBox);
        }

        BoxResponse boxResponse = BoxResponse.of(giftBox.getBox());
        EnvelopeResponse envelopeResponse = EnvelopeResponse.of(giftBox.getLetter().getEnvelope());
        List<PhotoResponse> photos = photoReader.findAllByGiftBox(giftBox).stream()
            .map(PhotoResponse::of)
            .sorted(Comparator.comparingInt(PhotoResponse::sequence))
            .toList();
        List<StickerResponse> stickers = giftBoxStickerReader.findAllByGiftBox(giftBox).stream()
            .map(StickerResponse::of)
            .sorted(Comparator.comparingInt(StickerResponse::location))
            .toList();

        GiftResponse giftResponse = null;
        if (giftBox.getGift() != null) {
            giftResponse = GiftResponse.of(giftBox.getGift());
        }

        return GiftBoxResponse.of(giftBox, boxResponse, envelopeResponse, photos, stickers,
            giftResponse);
    }

    public Slice<GiftBoxesResponse> getGiftBoxes(LocalDateTime lastGiftBoxDate, String type,
        Pageable pageable) {
        Long memberId = SecurityUtil.getMemberId();
        Member member = memberReader.findById(memberId);

        if (type == null) {
            type = "all";
        }

        Slice<GiftBox> giftBoxSlice = giftBoxReader.searchBySlice(member, lastGiftBoxDate, type,
            pageable);

        String finalType = type;
        List<GiftBoxesResponse> giftBoxesResponses = giftBoxSlice.getContent().stream()
            .map(giftBox -> {
                Receiver receiver = receiverReader.findByMemberAndGiftBox(member, giftBox);

                return GiftBoxesResponse.of(finalType, giftBox, receiver, member);
            })
            .toList();

        return new SliceImpl<>(giftBoxesResponses, pageable, giftBoxSlice.hasNext());
    }
}
