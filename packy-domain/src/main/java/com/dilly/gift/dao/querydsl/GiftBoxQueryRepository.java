package com.dilly.gift.dao.querydsl;

import static com.dilly.gift.domain.QGiftBox.giftBox;
import static com.dilly.gift.domain.QReceiver.receiver;

import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.ReceiverStatus;
import com.dilly.member.domain.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GiftBoxQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Slice<GiftBox> searchSentGiftBoxesBySlice(Member member, LocalDateTime lastGiftBoxDate,
        Pageable pageable) {
        List<GiftBox> results = getSentGiftBoxes(member, lastGiftBoxDate, pageable);

        return checkLastPage(pageable, results);
    }

    public Slice<GiftBox> searchReceivedGiftBoxesBySlice(Member member,
        LocalDateTime lastGiftBoxDate,
        Pageable pageable) {
        List<GiftBox> results = getReceivedGiftBoxes(member, lastGiftBoxDate, pageable);

        return checkLastPage(pageable, results);
    }

    public Slice<GiftBox> searchAllGiftBoxesBySlice(Member member, LocalDateTime lastGiftBoxDate,
        Comparator<Object> comparator, Pageable pageable) {
        List<GiftBox> sentGiftBoxes = getSentGiftBoxes(member, lastGiftBoxDate, pageable);
        List<GiftBox> receivedGiftBoxes = getReceivedGiftBoxes(member, lastGiftBoxDate, pageable);

        List<GiftBox> results = new ArrayList<>();
        results.addAll(sentGiftBoxes);
        results.addAll(receivedGiftBoxes);
        results.sort(comparator.reversed());

        results = results.subList(0, Math.min(results.size(), pageable.getPageSize() + 1));

        return checkLastPage(pageable, results);
    }

    public Slice<GiftBox> searchReceivedGiftBoxesWithGiftBySlice(Member member,
        LocalDateTime lastGiftBoxDate,
        Pageable pageable) {
        List<GiftBox> results = jpaQueryFactory.select(giftBox)
            .from(receiver)
            .join(receiver.giftBox, giftBox)
            .where(
                ltReceivedDate(lastGiftBoxDate),
                receiver.member.eq(member),
                receiver.status.eq(ReceiverStatus.RECEIVED),
                giftBox.gift.isNotNull()
            )
            .orderBy(receiver.createdAt.desc())
            .limit(pageable.getPageSize() + 1L)
            .fetch();

        return checkLastPage(pageable, results);
    }

    private List<GiftBox> getSentGiftBoxes(Member member, LocalDateTime lastGiftBoxDate,
        Pageable pageable) {
        return jpaQueryFactory.selectFrom(giftBox)
            .where(
                ltGiftBoxDate(lastGiftBoxDate),
                giftBox.sender.eq(member),
                giftBox.senderDeleted.eq(false))
            .orderBy(giftBox.createdAt.desc())
            .limit(pageable.getPageSize() + 1L)
            .fetch();
    }

    private List<GiftBox> getReceivedGiftBoxes(Member member, LocalDateTime lastGiftBoxDate,
        Pageable pageable) {
        return jpaQueryFactory.select(giftBox)
            .from(receiver)
            .join(receiver.giftBox, giftBox)
            .where(
                ltReceivedDate(lastGiftBoxDate),
                receiver.member.eq(member),
                receiver.status.eq(ReceiverStatus.RECEIVED))
            .orderBy(receiver.createdAt.desc())
            .limit(pageable.getPageSize() + 1L)
            .fetch();
    }

    private BooleanExpression ltGiftBoxDate(LocalDateTime giftBoxDate) {
        if (giftBoxDate == null) {
            return null;
        }

        return giftBox.createdAt.lt(giftBoxDate);
    }

    private BooleanExpression ltReceivedDate(LocalDateTime giftBoxDate) {
        if (giftBoxDate == null) {
            return null;
        }

        return receiver.createdAt.lt(giftBoxDate);
    }

    private Slice<GiftBox> checkLastPage(Pageable pageable, List<GiftBox> results) {
        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            results.remove(results.size() - 1);
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
