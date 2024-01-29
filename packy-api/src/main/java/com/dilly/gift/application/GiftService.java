package com.dilly.gift.application;

import static com.dilly.gift.GiftBoxType.PRIVATE;

import com.dilly.exception.GiftBoxAlreadyOpenedException;
import com.dilly.gift.Box;
import com.dilly.gift.Envelope;
import com.dilly.gift.Gift;
import com.dilly.gift.GiftBox;
import com.dilly.gift.GiftType;
import com.dilly.gift.Letter;
import com.dilly.gift.domain.BoxReader;
import com.dilly.gift.domain.EnvelopeReader;
import com.dilly.gift.domain.GiftBoxReader;
import com.dilly.gift.domain.GiftBoxStickerWriter;
import com.dilly.gift.domain.GiftBoxWriter;
import com.dilly.gift.domain.LetterWriter;
import com.dilly.gift.domain.PhotoWriter;
import com.dilly.gift.domain.ReceiverReader;
import com.dilly.gift.domain.ReceiverWriter;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.request.StickerRequest;
import com.dilly.gift.dto.response.BoxResponse;
import com.dilly.gift.dto.response.EnvelopeResponse;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.gift.dto.response.GiftResponse;
import com.dilly.gift.dto.response.PhotoResponse;
import com.dilly.gift.dto.response.StickerResponse;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.member.Member;
import com.dilly.member.domain.MemberReader;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GiftService {

    private final GiftBoxReader giftBoxReader;
    private final GiftBoxWriter giftBoxWriter;
    private final BoxReader boxReader;
    private final EnvelopeReader envelopeReader;
    private final LetterWriter letterWriter;
    private final PhotoWriter photoWriter;
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
                giftBoxRequest.senderName(), giftBoxRequest.receiverName());
        }

        for (PhotoRequest photoRequest : giftBoxRequest.photos()) {
            photoWriter.save(giftBox, photoRequest);
        }

        for (StickerRequest stickerRequest : giftBoxRequest.stickers()) {
            giftBoxStickerWriter.save(giftBox, stickerRequest);
        }

        return GiftBoxIdResponse.of(giftBox.getId(), giftBox.getUuid());
    }

    public GiftBoxResponse openGiftBox(Long giftBoxId) {
        Long memberId = SecurityUtil.getMemberId();
        Member receiver = memberReader.findById(memberId);

        GiftBox giftBox = giftBoxReader.findById(giftBoxId);

        Boolean hasReceiver = receiverReader.existsByGiftBoxId(giftBox);
        if (giftBox.getGiftBoxType().equals(PRIVATE) && Boolean.TRUE.equals(hasReceiver)) {
            throw new GiftBoxAlreadyOpenedException();
        }
        receiverWriter.save(receiver, giftBox);

        BoxResponse boxResponse = BoxResponse.of(giftBox.getBox());
        EnvelopeResponse envelopeResponse = EnvelopeResponse.of(giftBox.getLetter().getEnvelope());
        List<PhotoResponse> photos = giftBox.getPhotos().stream()
            .map(PhotoResponse::of)
            .sorted(Comparator.comparingInt(PhotoResponse::sequence))
            .toList();
        List<StickerResponse> stickers = giftBox.getGiftBoxStickers().stream()
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
}
