package com.dilly.gift.application;

import com.dilly.gift.Box;
import com.dilly.gift.Envelope;
import com.dilly.gift.Gift;
import com.dilly.gift.GiftBox;
import com.dilly.gift.GiftType;
import com.dilly.gift.Letter;
import com.dilly.gift.domain.BoxReader;
import com.dilly.gift.domain.EnvelopeReader;
import com.dilly.gift.domain.GiftBoxStickerWriter;
import com.dilly.gift.domain.GiftBoxWriter;
import com.dilly.gift.domain.LetterWriter;
import com.dilly.gift.domain.PhotoWriter;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.gift.dto.request.PhotoRequest;
import com.dilly.gift.dto.request.StickerRequest;
import com.dilly.gift.dto.response.GiftBoxIdResponse;
import com.dilly.gift.dto.response.GiftBoxResponse;
import com.dilly.global.utils.SecurityUtil;
import com.dilly.member.Member;
import com.dilly.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GiftService {

    private final GiftBoxWriter giftBoxWriter;
    private final BoxReader boxReader;
    private final EnvelopeReader envelopeReader;
    private final LetterWriter letterWriter;
    private final PhotoWriter photoWriter;
    private final GiftBoxStickerWriter giftBoxStickerWriter;
    private final MemberReader memberReader;

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
            Gift gift = Gift.builder()
                .giftType(GiftType.valueOf(giftBoxRequest.gift().type().toUpperCase()))
                .giftUrl(giftBoxRequest.gift().url())
                .build();

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

        return GiftBoxIdResponse.builder()
            .id(giftBox.getId())
            .uuid(giftBox.getUuid())
            .build();
    }
}
