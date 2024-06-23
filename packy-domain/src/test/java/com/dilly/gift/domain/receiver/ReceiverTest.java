package com.dilly.gift.domain.receiver;

import static com.dilly.MemberEnumFixture.NORMAL_MEMBER_RECEIVER;
import static org.assertj.core.api.Assertions.assertThat;

import com.dilly.GiftBoxFixture;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReceiverTest {

    @DisplayName("ReceiverStatus를 DELETED로 변경한다.")
    @Test
    void changeReceiverStatusToDELETED() {
        // given
        Member member = NORMAL_MEMBER_RECEIVER.createMember(1L);
        GiftBox giftBox = GiftBoxFixture.createGiftBoxFixture(member);
        Receiver receiver = Receiver.of(member, giftBox);

        // when
        receiver.delete();

        // then
        assertThat(receiver.getStatus()).isEqualTo(ReceiverStatus.DELETED);
    }
}
