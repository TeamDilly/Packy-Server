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
            createMockGiftBox(member1);
        }
    }

    @DisplayName("보낸 선물박스를 최신순으로 처음 4개 조회한다.")
    @Test
    void getSentGiftBoxes() {
        // given
        Long latestgiftBoxId = giftBoxRepository.findTopByOrderByIdDesc().getId();

        // when
        Slice<GiftBox> giftBoxSlice = giftBoxQueryRepository.searchSentGiftBoxesBySlice(member1,
            null,
            PageRequest.ofSize(4));
        Long first = giftBoxSlice.getContent().get(0).getId();
        Long last = giftBoxSlice.getContent().get(3).getId();

        // then
        assertThat(giftBoxSlice.isFirst()).isTrue();
        assertThat(giftBoxSlice.getContent()).hasSize(4);
        assertThat(first).isEqualTo(latestgiftBoxId);
        assertThat(last).isEqualTo(latestgiftBoxId - 3);
        for (GiftBox giftBox : giftBoxSlice.getContent()) {
            assertThat(giftBox.getSender()).isEqualTo(member1);
        }
    }

    @DisplayName("lastGiftBoxDate 이전에 보낸 선물박스 4개를 조회한다.")
    @Test
    void getSentGiftBoxesBeforeLastGiftBoxDate() {
        // given
        GiftBox lastGiftBox = giftBoxRepository.findTopByOrderByIdDesc();
        Long lastgiftBoxId = lastGiftBox.getId() - 8;
        LocalDateTime lastGiftBoxDate = giftBoxRepository.findById(lastgiftBoxId).orElseThrow()
            .getCreatedAt();

        // when
        Slice<GiftBox> giftBoxSlice = giftBoxQueryRepository.searchSentGiftBoxesBySlice(member1,
            lastGiftBoxDate,
            PageRequest.ofSize(4));
        Long first = giftBoxSlice.getContent().get(0).getId();
        Long last = giftBoxSlice.getContent().get(3).getId();

        // then
        assertThat(giftBoxSlice.getContent()).hasSize(4);
        assertThat(first).isEqualTo(lastgiftBoxId - 1);
        assertThat(last).isEqualTo(lastgiftBoxId - 4);
        for (GiftBox giftBox : giftBoxSlice.getContent()) {
            assertThat(giftBox.getSender()).isEqualTo(member1);
        }
    }
}
