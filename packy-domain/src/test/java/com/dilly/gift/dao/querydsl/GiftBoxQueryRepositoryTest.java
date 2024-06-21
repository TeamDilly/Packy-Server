package com.dilly.gift.dao.querydsl;

import com.dilly.global.RepositoryTestSupport;
import com.dilly.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;

class GiftBoxQueryRepositoryTest extends RepositoryTestSupport {

    Member member1;

    @BeforeEach
    void setUp() {
        member1 = memberRepository.findById(1L).orElseThrow();

        for (int i = 0; i < 15; i++) {
            createMockGiftBox(member1);
        }
    }

// TODO: TSID 적용에 따라 수정 필요

//    @DisplayName("보낸 선물박스를 최신순으로 처음 4개 조회한다.")
//    @Test
//    void getSentGiftBoxes() {
//        // given
//        Long latestgiftBoxId = giftBoxRepository.findTopByOrderByIdDesc().getId();
//
//        // when
//        Slice<GiftBox> giftBoxSlice = giftBoxQueryRepository.searchSentGiftBoxesBySlice(member1,
//            null,
//            PageRequest.ofSize(4));
//        Long first = giftBoxSlice.getContent().get(0).getId();
//        Long last = giftBoxSlice.getContent().get(3).getId();
//
//        // then
//        assertThat(giftBoxSlice.isFirst()).isTrue();
//        assertThat(giftBoxSlice.getContent()).hasSize(4);
//        assertThat(first).isEqualTo(latestgiftBoxId);
//        assertThat(last).isEqualTo(latestgiftBoxId - 3);
//        for (GiftBox giftBox : giftBoxSlice.getContent()) {
//            assertThat(giftBox.getSender()).isEqualTo(member1);
//        }
//    }
}
