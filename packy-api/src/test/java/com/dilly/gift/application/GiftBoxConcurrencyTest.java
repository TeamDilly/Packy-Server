package com.dilly.gift.application;

import static com.dilly.GiftBoxFixture.sendGiftBoxFixtureWithGift;
import static com.dilly.LetterFixture.createLetterFixture;
import static com.dilly.MemberEnumFixture.NORMAL_MEMBER_RECEIVER;
import static com.dilly.MemberEnumFixture.NORMAL_MEMBER_SENDER;
import static org.assertj.core.api.Assertions.assertThat;

import com.dilly.gift.adaptor.GiftBoxWriter;
import com.dilly.gift.adaptor.LetterWriter;
import com.dilly.gift.adaptor.ReceiverReader;
import com.dilly.gift.domain.giftbox.GiftBox;
import com.dilly.gift.domain.letter.Letter;
import com.dilly.gift.domain.receiver.Receiver;
import com.dilly.global.WithCustomMockUserSecurityContextFactory;
import com.dilly.member.adaptor.MemberReader;
import com.dilly.member.adaptor.MemberWriter;
import com.dilly.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.profiles.active=test"
)
@TestPropertySource(locations = {"classpath:application-test.yml"})
class GiftBoxConcurrencyTest {

    @Autowired
    private GiftBoxService giftBoxService;

    @Autowired
    private MemberReader memberReader;

    @Autowired
    private MemberWriter memberWriter;

    @Autowired
    private GiftBoxWriter giftBoxWriter;

    @Autowired
    private LetterWriter letterWriter;

    @Autowired
    private ReceiverReader receiverReader;

    @Autowired
    private WithCustomMockUserSecurityContextFactory withCustomMockUserSecurityContextFactory;

    private Member MEMBER_SENDER;

    private final String SENDER_ID = "1";

    private Letter letter;

    @BeforeEach
    void setUp() {
        Long senderId = Long.parseLong(SENDER_ID);

        MEMBER_SENDER = memberWriter.save(NORMAL_MEMBER_SENDER.createMember(senderId));

        letter = letterWriter.save(createLetterFixture());
    }

    @DisplayName("선물박스 열기 동시성 테스트")
    @Test
    void multipleUserOpenGiftBox() throws InterruptedException {
        // given
        int memberCount = 10;
        int giftBoxAmount = 1;

        List<Member> receivers = new ArrayList<>();
        Long lastMemberId = memberReader.count();
        for (long i = lastMemberId + 1; i <= lastMemberId + memberCount; i++) {
            Member member = memberWriter.save(NORMAL_MEMBER_RECEIVER.createMember());
            receivers.add(member);
        }

        GiftBox giftBox = giftBoxWriter.save(
            sendGiftBoxFixtureWithGift(MEMBER_SENDER, letter));

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(memberCount);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        Long receiverBefore = receiverReader.countByGiftBox(giftBox);

        // when
        for (int i = 0; i < memberCount; i++) {
            Member member = receivers.get(i);
            Long memberId = member.getId();
            executorService.submit(() -> {
                try {
                    createSecurityContextWithMockUser(memberId.toString());
                    giftBoxService.openGiftBox(giftBox.getId());
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                    failCount.incrementAndGet();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        Long receiverAfter = receiverReader.countByGiftBox(giftBox);
        List<Receiver> receiverList = receiverReader.findByGiftBox(giftBox);

        System.out.println("successCount = " + successCount);
        System.out.println("failCount = " + failCount);

        System.out.println("receiverBefore = " + receiverBefore);
        System.out.println("receiverAfter = " + receiverAfter);

        System.out.println("receiver 목록 출력");
        for (Receiver receiver : receiverList) {
            System.out.println("memberId = " + receiver.getMember().getId());
        }

        // then
        assertThat(successCount.get()).isEqualTo(giftBoxAmount);
        assertThat(receiverAfter).isEqualTo(giftBoxAmount);
    }

    private void createSecurityContextWithMockUser(String memberId) {
        SecurityContext securityContext = withCustomMockUserSecurityContextFactory.createSecurityContext(
            memberId);
        SecurityContextHolder.setContext(securityContext);
    }
}
