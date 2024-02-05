package com.dilly.gift.dao.querydsl;

import static com.dilly.gift.domain.QGiftBox.giftBox;
import static com.dilly.gift.domain.QReceiver.receiver;

import com.dilly.exception.ErrorCode;
import com.dilly.exception.UnsupportedException;
import com.dilly.gift.adaptor.ReceiverReader;
import com.dilly.gift.domain.GiftBox;
import com.dilly.gift.domain.Receiver;
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
    private final ReceiverReader receiverReader;

    public Slice<GiftBox> searchBySlice(Member member, LocalDateTime lastGiftBoxDate, String type,
        Pageable pageable) {
        List<GiftBox> results = new ArrayList<>();

        switch (type) {
            case "sent" -> results = getSentGiftBoxes(member, lastGiftBoxDate, pageable);
            case "received" -> results = getReceivedGiftBoxes(member, lastGiftBoxDate, pageable);
            case "all" -> {
                List<GiftBox> sentGiftBoxes = getSentGiftBoxes(member, lastGiftBoxDate, pageable);
                List<GiftBox> receivedGiftBoxes = getReceivedGiftBoxes(member, lastGiftBoxDate,
                    pageable);
                results.addAll(sentGiftBoxes);
                results.addAll(receivedGiftBoxes);
                results.sort(getCreatedAtComparator(member).reversed());

                results = results.subList(0, Math.min(results.size(), pageable.getPageSize() + 1));
            }
            default -> throw new UnsupportedException(ErrorCode.UNSUPPORTED_GIFTBOX_TYPE);
        }

        return checkLastPage(pageable, results);
    }

    // TODO: Repository간 의존 괜찮은가?
    private Comparator<Object> getCreatedAtComparator(Member member) {
        return Comparator.comparing((Object obj) -> {
            GiftBox giftBox = (GiftBox) obj;
            LocalDateTime createdAt;

            if (giftBox.getSender().equals(member)) {
                createdAt = giftBox.getCreatedAt();
            } else {
                Receiver receiver = receiverReader.findByMemberAndGiftBox(member, giftBox);
                createdAt = receiver.getCreatedAt();
            }

            return createdAt;
        });
    }

    private List<GiftBox> getSentGiftBoxes(Member member, LocalDateTime lastGiftBoxDate,
        Pageable pageable) {
        return jpaQueryFactory.selectFrom(giftBox)
            .where(
                ltGiftBoxDate(lastGiftBoxDate),
                giftBox.sender.eq(member))
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
                ltGiftBoxDate(lastGiftBoxDate),
                receiver.member.eq(member))
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

    private Slice<GiftBox> checkLastPage(Pageable pageable, List<GiftBox> results) {
        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            results.remove(results.size() - 1);
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }
}
