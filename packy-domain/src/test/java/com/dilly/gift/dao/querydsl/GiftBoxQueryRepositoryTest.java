package com.dilly.gift.dao.querydsl;

import static org.assertj.core.api.Assertions.assertThat;

import com.dilly.gift.domain.GiftBox;
import com.dilly.global.RepositoryTestSupport;
import com.dilly.member.domain.Member;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

class GiftBoxQueryRepositoryTest extends RepositoryTestSupport {

    Member member1;

    @BeforeEach
    void setUp() {
        member1 = memberRepository.findById(1L).orElseThrow();

        for (int i = 0; i < 15; i++) {
            createMockGiftBoxWithGift(member1);
        }
    }

    @DisplayName("보낸 선물박스를 최신순으로 4개 조회한다.")
    @Test
    void getGiftBoxes() {
        // given
        Long latestgiftBoxId = giftBoxRepository.count();

        // when
        Slice<GiftBox> giftBoxSlice = giftBoxQueryRepository.searchBySlice(member1, null, "sent",
            PageRequest.ofSize(4));
        Long first = giftBoxSlice.getContent().get(0).getId();
        Long last = giftBoxSlice.getContent().get(3).getId();

        // then
        assertThat(giftBoxSlice.isFirst()).isTrue();
        assertThat(giftBoxSlice.getContent()).hasSize(4);
        assertThat(giftBoxSlice.getContent().get(0).getId()).isEqualTo(latestgiftBoxId);
        assertThat(giftBoxSlice.getContent().get(3).getId()).isEqualTo(latestgiftBoxId - 3);
    }

    @DisplayName("lastGiftBoxDate 이전에 보낸 선물박스 4개를 조회한다.")
    @Test
    void getGiftBoxesBeforeLastGiftBoxDate() {
        // given
        GiftBox lastGiftBox = giftBoxRepository.findTopByOrderByIdDesc();
        Long latestgiftBoxId = lastGiftBox.getId() - 8;
        LocalDateTime lastGiftBoxDate = giftBoxRepository.findById(latestgiftBoxId).orElseThrow().getCreatedAt();

        // when
        Slice<GiftBox> giftBoxSlice = giftBoxQueryRepository.searchBySlice(member1, lastGiftBoxDate, "sent",
            PageRequest.ofSize(4));

        // then
        assertThat(giftBoxSlice.getContent()).hasSize(4);
        assertThat(giftBoxSlice.getContent().get(0).getId()).isEqualTo(latestgiftBoxId - 1);
        assertThat(giftBoxSlice.getContent().get(3).getId()).isEqualTo(latestgiftBoxId - 4);
    }
}
