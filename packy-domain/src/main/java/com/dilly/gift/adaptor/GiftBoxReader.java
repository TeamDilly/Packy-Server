package com.dilly.gift.adaptor;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.dao.GiftBoxRepository;
import com.dilly.gift.dao.querydsl.GiftBoxQueryRepository;
import com.dilly.gift.domain.giftbox.DeliverStatus;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.member.domain.Member;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftBoxReader {

    private final GiftBoxRepository giftBoxRepository;
    private final GiftBoxQueryRepository giftBoxQueryRepository;

    public GiftBox findById(Long giftBoxId) {
        return giftBoxRepository.findById(giftBoxId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.GIFTBOX_NOT_FOUND));
    }

    public GiftBox findByUuid(String uuid) {
        return giftBoxRepository.findByUuid(uuid)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.GIFTBOX_NOT_FOUND));
    }

    public GiftBox findByLetter(Letter letter) {
        return giftBoxRepository.findByLetter(letter);
    }

    public List<GiftBox> findTop6BySenderAndDeliverStatusAndSenderDeletedOrderByCreatedAtDesc(
        Member member,
        DeliverStatus deliverStatus) {
        return giftBoxRepository.findTop6BySenderAndDeliverStatusAndSenderDeletedOrderByCreatedAtDesc(
            member,
            deliverStatus, false);
    }

    public Slice<GiftBox> searchSentGiftBoxesBySlice(Member member, LocalDateTime lastGiftBoxDate,
        Pageable pageable) {
        return giftBoxQueryRepository.searchSentGiftBoxesBySlice(member, lastGiftBoxDate, pageable);
    }

    public Slice<GiftBox> searchReceivedGiftBoxesBySlice(Member member,
        LocalDateTime lastGiftBoxDate,
        Pageable pageable) {
        return giftBoxQueryRepository.searchReceivedGiftBoxesBySlice(member, lastGiftBoxDate,
            pageable);
    }

    public Slice<GiftBox> searchAllGiftBoxesBySlice(Member member, LocalDateTime lastGiftBoxDate,
        Comparator<Object> comparator, Pageable pageable) {
        return giftBoxQueryRepository.searchAllGiftBoxesBySlice(member, lastGiftBoxDate, comparator,
            pageable);
    }

    public Slice<GiftBox> searchReceivedGiftBoxesWithGiftBySlice(Member member,
        LocalDateTime lastGiftBoxDate, Pageable pageable) {
        return giftBoxQueryRepository.searchReceivedGiftBoxesWithGiftBySlice(member,
            lastGiftBoxDate, pageable);
    }

    public GiftBox findTopByOrderByIdDesc() {
        return giftBoxRepository.findTopByOrderByIdDesc();
    }
}
