package com.dilly.gift.domain;

import com.dilly.gift.GiftBox;
import com.dilly.gift.MemberGiftBox;
import com.dilly.gift.dao.MemberGiftBoxRepository;
import com.dilly.gift.dto.request.GiftBoxRequest;
import com.dilly.member.Member;
import com.dilly.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberGiftBoxWriter {

    private final MemberGiftBoxRepository memberGiftBoxRepository;
    private final MemberReader memberReader;

    public void save(Long memberId, GiftBox giftBox, GiftBoxRequest giftBoxRequest) {
        Member member = memberReader.findById(memberId);
        memberGiftBoxRepository.save(MemberGiftBox.builder()
            .sender(member)
            .giftBox(giftBox)
            .senderName(giftBoxRequest.senderName())
            .receiverName(giftBoxRequest.receiverName())
            .build());
    }
}
