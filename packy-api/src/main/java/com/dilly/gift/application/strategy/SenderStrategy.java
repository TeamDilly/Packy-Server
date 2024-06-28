package com.dilly.gift.application.strategy;

import com.dilly.application.FileService;
import com.dilly.exception.GiftBoxAlreadyDeletedException;
import com.dilly.gift.adaptor.GiftBoxStickerWriter;
import com.dilly.gift.adaptor.GiftBoxWriter;
import com.dilly.gift.adaptor.LetterWriter;
import com.dilly.gift.adaptor.PhotoWriter;
import com.dilly.gift.domain.gift.GiftType;
import com.dilly.gift.domain.giftbox.DeliverStatus;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SenderStrategy implements GiftBoxStrategy {

    private final FileService fileService;
    private final GiftBoxWriter giftBoxWriter;
    private final LetterWriter letterWriter;
    private final PhotoWriter photoWriter;
    private final GiftBoxStickerWriter giftBoxStickerWriter;

    @Override
    public void open(Member member, GiftBox giftBox) {
        if (Boolean.TRUE.equals(giftBox.getSenderDeleted())) {
            throw new GiftBoxAlreadyDeletedException();
        }
    }

    @Override
    public void delete(Member member, GiftBox giftBox) {
        if (giftBox.getDeliverStatus().equals(DeliverStatus.DELIVERED)) {
            giftBox.delete();
        } else if (giftBox.getDeliverStatus().equals(DeliverStatus.WAITING)) {
            // 아직 카카오톡으로 보내지 않았다면 선물박스 내부 요소와 S3에 저장된 이미지 삭제
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
    }
}
