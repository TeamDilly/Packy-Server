package com.dilly.gift.adaptor;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.entitynotfound.EntityNotFoundException;
import com.dilly.gift.dao.GiftBoxRepository;
import com.dilly.gift.dao.querydsl.GiftBoxQueryRepository;
import com.dilly.gift.domain.GiftBox;
import com.dilly.member.domain.Member;
import java.time.LocalDateTime;
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

    public Slice<GiftBox> searchBySlice(Member member, LocalDateTime lastGiftBoxDate, String type,
        Pageable pageable) {
        return giftBoxQueryRepository.searchBySlice(member, lastGiftBoxDate, type, pageable);
    }

}
